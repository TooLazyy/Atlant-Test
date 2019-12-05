package com.example.i_web_socket

data class WebSocketSubscribeRequest<T>(
    val optionFilter: String,
    val subscribeRequest: Any,
    val responseType: Class<T>
)

data class WebSocketUnsubscribeRequest(
    val unsubscribeRequest: Any,
    val subscribeRequest: Any
)