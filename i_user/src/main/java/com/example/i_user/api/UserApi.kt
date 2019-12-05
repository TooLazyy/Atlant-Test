package com.example.i_user.api

import com.example.base.network.TOKEN_HEADER
import com.example.i_user.response.UserResponseObj
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("accounts/current")
    suspend fun getUserProfile(@Header(TOKEN_HEADER) header: String = ""): UserResponseObj
}