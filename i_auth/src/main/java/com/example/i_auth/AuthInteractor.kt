package com.example.i_auth

import com.example.base.RequestResult
import com.example.base.dispatchers.DispatchersProvider
import com.example.base.doOnSuccess
import com.example.i_auth.repository.AuthRepository
import com.example.i_session.SessionInteractor
import kotlinx.coroutines.withContext

class AuthInteractor(
    private val repository: AuthRepository,
    private val sessionInteractor: SessionInteractor,
    private val dispatchers: DispatchersProvider
) {

    suspend fun auth(email: String, password: String): RequestResult<String> =
        repository
            .auth(email, password)
            .doOnSuccess {
                withContext(dispatchers.default()) {
                    sessionInteractor.updateToken(it)
                }
            }
}