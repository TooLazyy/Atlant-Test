package com.example.i_session.interceptors

import com.example.base.SimpleKeyValueStorage
import com.example.base.network.AUTH_HEADER
import com.example.base.network.TOKEN_HEADER
import com.example.i_token.TokenData
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(
    private val storage: SimpleKeyValueStorage<TokenData>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = addTokenHeaderIfNeeded(originalRequest)
        return chain.proceed(newRequest)
    }

    private fun addTokenHeaderIfNeeded(originalRequest: Request): Request {
        return if (needToAddToken(originalRequest)) {
            val tokenData = storage.get() ?: return originalRequest
            originalRequest
                .newBuilder()
                .removeHeader(TOKEN_HEADER)
                .addHeader(AUTH_HEADER, tokenData.token)
                .build()
        } else {
            originalRequest
        }
    }

    private fun needToAddToken(request: Request): Boolean = request.header(TOKEN_HEADER) != null
}