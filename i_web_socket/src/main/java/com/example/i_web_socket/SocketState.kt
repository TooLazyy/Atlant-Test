package com.example.i_web_socket

sealed class SocketState {

    object Idle : SocketState()
    object Connecting : SocketState()
    object Closing : SocketState()
    object Connected : SocketState()
    object Error : SocketState()
}