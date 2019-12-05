package com.example.i_session.interceptors

import com.example.base.network.*
import com.example.i_session.token_parser.TokenParser
import com.example.i_token.TokenData
import com.google.gson.Gson
import okhttp3.*

class TokenRefreshHelper(
    private val tokenParser: TokenParser,
    private val gson: Gson
) {

    fun refreshToken(
        token: String,
        chain: Interceptor.Chain,
        response: Response
    ): TokenData? {
        closeResponse(response)
        val refreshRequest = createRefreshTokenRequest(token)
        val refreshResponse = chain.proceed(refreshRequest)
        return if (refreshResponse.isSuccessful) {
            val responseString = refreshResponse.body()?.string() ?: return null
            val tokenResponse = gson.fromJson(responseString, AuthResponseObj::class.java)
            tokenParser.parseToken(tokenResponse.token)
        } else {
            null
        }
    }

    private fun closeResponse(response: Response) {
        try {
            response.close()
        } catch (ignore: Exception) {
        }
    }

    private fun createRefreshTokenRequest(token: String): Request {
        return Request.Builder()
            .url(BASE_URL + REFRESH_TOKEN_URL)
            .method(POST_METHOD, createEmptyRequestBody())
            .addHeader(AUTH_HEADER, token)
            .build()
    }

    private fun createEmptyRequestBody(): RequestBody =
        RequestBody.create(MediaType.parse("text"), "")
}