package com.example.i_session.token_parser

import com.google.gson.annotations.SerializedName

data class TokenPayload(
    @SerializedName("session_id") val sesstionId: String?
)