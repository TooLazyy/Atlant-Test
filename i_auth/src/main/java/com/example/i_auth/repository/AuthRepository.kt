package com.example.i_auth.repository

import com.example.base.RequestResult

interface AuthRepository {

    suspend fun auth(email: String, password: String): RequestResult<String>
}