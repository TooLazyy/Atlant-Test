package com.example.base

inline fun <T> wrapResult(block: () -> T): RequestResult<T> {
    return try {
        RequestResult.Success(block())
    } catch (ex: Exception) {
        RequestResult.Error(ex)
    }
}

suspend fun <T> RequestResult<T>.doOnSuccess(action: suspend (T) -> Unit): RequestResult<T> {
    if (this is RequestResult.Success) {
        action(this.data)
    }
    return this
}

suspend fun <T> RequestResult<T>.doOnNext(action: suspend () -> Unit): RequestResult<T> {
    action()
    return this
}