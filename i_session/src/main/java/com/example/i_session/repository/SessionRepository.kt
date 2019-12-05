package com.example.i_session.repository

import com.example.base.RequestResult

interface SessionRepository {

    suspend fun logout(sessionId: String): RequestResult<Unit>
}