package com.example.i_auth.api

import com.example.i_auth.request.AuthRequest
import com.example.base.network.AuthResponseObj
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("accounts/auth")
    suspend fun auth(@Body request: AuthRequest): AuthResponseObj
}