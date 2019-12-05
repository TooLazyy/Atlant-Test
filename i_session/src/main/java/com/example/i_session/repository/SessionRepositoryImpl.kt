package com.example.i_session.repository

import com.example.base.RequestResult
import com.example.base.dispatchers.DispatchersProvider
import com.example.base.wrapResult
import com.example.i_session.api.SessionApi
import com.example.i_session.request.LogoutRequest
import kotlinx.coroutines.withContext

class SessionRepositoryImpl(
    private val dispatchers: DispatchersProvider,
    private val api: SessionApi
) : SessionRepository {

    override suspend fun logout(sessionId: String): RequestResult<Unit> =
        withContext(dispatchers.default()) {
            wrapResult {
                api.logout(LogoutRequest(sessionId))
            }
        }
}