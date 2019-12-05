package com.example.base

interface SimpleKeyValueStorage<T> {

    fun get(): T?

    fun put(value: T)

    fun clear()
}