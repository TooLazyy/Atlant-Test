package com.example.i_session.api

import com.example.i_session.request.LogoutRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SessionApi {

    @POST("accounts/sessions/end")
    suspend fun logout(
        @Body request: LogoutRequest,
        @Header("Authorization") token: String = ""
    )
}