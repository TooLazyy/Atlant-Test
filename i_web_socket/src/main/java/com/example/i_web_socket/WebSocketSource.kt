package com.example.i_web_socket

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow

class WebSocketSource(
    private val socketWrapper: WebSocketWrapper
) {

    fun connect() = socketWrapper.connect()

    fun disconnect() = socketWrapper.disconnect()

    fun disconnectSilent() = socketWrapper.disconnectSilent()

    fun <T : BaseSocketResponse> subscribe(request: WebSocketSubscribeRequest<T>): Flow<T> =
        socketWrapper.subscribe(request)

    fun unsubscribe(request: WebSocketUnsubscribeRequest) = socketWrapper.unsubscribe(request)

    fun subscribeToSocketState(): ReceiveChannel<SocketState> = socketWrapper.socketStateChannel
}

class Temp : BaseSocketResponse()