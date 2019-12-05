package com.example.base.dispatchers

import kotlinx.coroutines.Dispatchers

class DispatchersProviderImpl : DispatchersProvider {

    override fun main() = Dispatchers.Main

    override fun default() = Dispatchers.Default
}