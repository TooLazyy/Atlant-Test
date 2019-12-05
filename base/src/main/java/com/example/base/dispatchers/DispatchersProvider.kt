package com.example.base.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {

    fun main(): CoroutineDispatcher

    fun default(): CoroutineDispatcher
}