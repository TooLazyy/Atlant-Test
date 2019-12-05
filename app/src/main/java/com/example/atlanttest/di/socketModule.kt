package com.example.atlanttest.di

import com.example.base.network.WEB_SOCKET_URL
import com.example.i_web_socket.WebSocketSource
import com.example.i_web_socket.WebSocketWrapper
import org.koin.core.qualifier.named
import org.koin.dsl.module

val socketModule = module {

    single { WEB_SOCKET_URL }
    single { WebSocketWrapper(get(), get(named(WEB_SOCKET_CLIENT)), get()) }
    single { WebSocketSource(get()) }
}