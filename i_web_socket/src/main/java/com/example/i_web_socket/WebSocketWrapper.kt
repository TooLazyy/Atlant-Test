package com.example.i_web_socket

import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import okhttp3.*

private const val SOCKET_RECONNECT_DELAY = 3000L
private const val SOCKET_CLOSE_CODE = 1000

class WebSocketWrapper(
    socketUrl: String,
    private val client: OkHttpClient,
    private val gson: Gson
) {

    private var webSocket: WebSocket? = null
    private var socketState: SocketState = SocketState.Idle
    private val socketRequest: Request =
        Request.Builder()
            .url(socketUrl)
            .build()
    private val socketListener: WebSocketListener = createSocketListener()
    private var socketReconnectJob: Job? = null

    private val socketMessagesChannel: BroadcastChannel<String> = BroadcastChannel(1)

    private val socketStateBroadcastChannel: BroadcastChannel<SocketState> = BroadcastChannel(1)
    val socketStateChannel: ReceiveChannel<SocketState>
        get() = socketStateBroadcastChannel.openSubscription()

    private val socketSubscribeMissedMessages: MutableSet<String> = mutableSetOf()
    private val socketSubscribeCachedMessages: MutableSet<String> = mutableSetOf()

    fun connect() {
        if (socketState != SocketState.Idle) {
            return
        }
        makeConnection()
    }

    fun disconnect() {
        disconnectInternal()
        socketSubscribeCachedMessages.clear()
        socketSubscribeMissedMessages.clear()
    }

    fun disconnectSilent() {
        disconnectInternal()
    }

    fun <T : BaseSocketResponse> subscribe(request: WebSocketSubscribeRequest<T>): Flow<T> {
        val subscribeMessage = gson.toJson(request.subscribeRequest)
        if (socketState == SocketState.Connected) {
            socketSubscribeCachedMessages.add(subscribeMessage)
            sendMessageInternal(subscribeMessage)
        } else {
            socketSubscribeMissedMessages.add(subscribeMessage)
        }
        return socketMessagesChannel
            .openSubscription()
            .consumeAsFlow()
            .filter {
                val response = gson.fromJson(it, BaseSocketResponse::class.java)
                response.op == request.optionFilter
            }
            .map { gson.fromJson(it, request.responseType) }
    }

    fun unsubscribe(request: WebSocketUnsubscribeRequest) {
        if (socketState == SocketState.Connected) {
            sendMessageInternal(gson.toJson(request.unsubscribeRequest))
        } else {
            val subscribeMessage = gson.toJson(request.subscribeRequest)
            socketSubscribeCachedMessages.removeAll { it == subscribeMessage }
            socketSubscribeMissedMessages.removeAll { it == subscribeMessage }
        }
    }

    private fun disconnectInternal() {
        socketReconnectJob?.cancel()

        if (socketState == SocketState.Connected) {
            webSocket?.close(SOCKET_CLOSE_CODE, null)
        }

        socketState = SocketState.Idle
    }

    private fun makeConnection() {
        socketState = SocketState.Connecting
        webSocket = createWebSocket()
    }

    private fun sendMessageInternal(message: String) {
        webSocket?.send(message)
    }

    private fun createWebSocket(): WebSocket =
        client.newWebSocket(socketRequest, socketListener)

    private fun createSocketListener(): WebSocketListener =
        object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                socketState = SocketState.Connected
                subscribeToMissedTopics()
                subscribeToCachedTopics()
                runBlocking {
                    socketStateBroadcastChannel.send(SocketState.Connected)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                socketState = SocketState.Error
                reconnect()
                runBlocking {
                    socketStateBroadcastChannel.send(SocketState.Error)
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                socketState = SocketState.Idle
                runBlocking {
                    socketStateBroadcastChannel.send(SocketState.Idle)
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                socketState = SocketState.Closing
                runBlocking {
                    socketStateBroadcastChannel.send(SocketState.Closing)
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                runBlocking {
                    socketMessagesChannel.send(text)
                }
            }
        }

    private fun subscribeToMissedTopics() {
        val iterator = socketSubscribeMissedMessages.iterator()
        while (iterator.hasNext()) {
            val message = iterator.next()
            sendMessageInternal(message)
            socketSubscribeCachedMessages.add(message)
            iterator.remove()
        }
    }

    private fun subscribeToCachedTopics() {
        socketSubscribeCachedMessages.forEach {
            sendMessageInternal(it)
        }
    }

    private fun reconnect() {
        socketReconnectJob?.cancel()

        socketReconnectJob = GlobalScope.launch {
            delay(SOCKET_RECONNECT_DELAY)
            makeConnection()
        }
    }
}