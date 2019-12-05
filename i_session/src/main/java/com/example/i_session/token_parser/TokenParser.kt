package com.example.i_session.token_parser

import android.util.Base64
import com.example.i_token.TokenData
import com.google.gson.Gson

class TokenParser(
    private val gson: Gson
) {

    fun parseToken(tokenResponse: String): TokenData {
        val decodedTokenPayload = getTokenPayloadRaw(tokenResponse)
        val tokenPayload =
            gson.fromJson(String(decodedTokenPayload, Charsets.UTF_8), TokenPayload::class.java)
        return TokenData(
            tokenResponse,
            tokenPayload.sesstionId ?: ""
        )
    }

    private fun getTokenPayloadRaw(tokenResponse: String): ByteArray {
        val tokenPayloadEncoded = tokenResponse.split(".")[1]
        return Base64.decode(tokenPayloadEncoded, Base64.DEFAULT)
    }
}