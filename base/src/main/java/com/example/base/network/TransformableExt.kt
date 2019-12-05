package com.example.base.network

fun <T> Collection<Transformable<T>>.transform(): List<T> {
    return map { it.transform() }
}