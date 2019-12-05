package com.example.base.network

import com.google.gson.annotations.SerializedName

data class AuthResponseObj(
    @SerializedName("token") val token: String
)