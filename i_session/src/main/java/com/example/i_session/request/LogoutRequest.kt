package com.example.i_session.request

import com.google.gson.annotations.SerializedName

data class LogoutRequest(
    @SerializedName("session_id") val sessionId: String
)