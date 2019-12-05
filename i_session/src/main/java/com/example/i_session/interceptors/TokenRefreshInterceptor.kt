package com.example.i_session.interceptors

import com.example.base.SimpleKeyValueStorage
import com.example.base.network.AUTH_HEADER
import com.example.i_token.TokenData
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenRefreshInterceptor(
    private val storage: SimpleKeyValueStorage<TokenData>,
    private val refreshTokenHelper: TokenRefreshHelper
) : Interceptor {

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)
        return if (!response.isSuccessful &&
            response.code() == 401
        ) {
            refreshTokenIfPossible(
                originalRequest,
                chain,
                response
            ) ?: response
        } else {
            response
        }
    }

    private fun refreshTokenIfPossible(
        request: Request,
        chain: Interceptor.Chain,
        response: Response
    ): Response? {
        val tokenData = storage.get()
        return if (tokenData != null) {
            val newTokenData = refreshTokenHelper.refreshToken(tokenData.token, chain, response)
            if (newTokenData != null) {
                storage.put(newTokenData)
                retryRequest(request, chain)
            } else {
                null
            }
        } else {
            null
        }
    }

    private fun retryRequest(
        originalRequest: Request,
        chain: Interceptor.Chain
    ): Response {
        val newRequest =
            originalRequest
                .newBuilder()
                .removeHeader(AUTH_HEADER)
                .addHeader(AUTH_HEADER, storage.get()?.token ?: "")
                .build()
        return chain.proceed(newRequest)
    }
}