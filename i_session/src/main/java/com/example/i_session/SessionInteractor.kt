package com.example.i_session

import com.example.base.RequestResult
import com.example.base.SimpleKeyValueStorage
import com.example.base.doOnNext
import com.example.i_session.repository.SessionRepository
import com.example.i_session.token_parser.TokenParser
import com.example.i_token.TokenData

class SessionInteractor(
    private val tokenStorage: SimpleKeyValueStorage<TokenData>,
    private val repository: SessionRepository,
    private val tokenParser: TokenParser
) {

    val isAuthorized: Boolean
        get() = tokenStorage.get() != null

    fun updateToken(tokenResponse: String) {
        val tokenData = tokenParser.parseToken(tokenResponse)
        tokenStorage.put(tokenData)
    }

    suspend fun logout(): RequestResult<Unit> {
        val sessionId = tokenStorage.get()?.sessionId ?: ""
        return repository
            .logout(sessionId)
            .doOnNext {
                tokenStorage.clear()
            }
    }
}