package com.example.i_auth.repository

import com.example.base.RequestResult
import com.example.base.dispatchers.DispatchersProvider
import com.example.base.wrapResult
import com.example.i_auth.api.AuthApi
import com.example.i_auth.request.AuthRequest
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val dispatchers: DispatchersProvider
) : AuthRepository {

    override suspend fun auth(email: String, password: String): RequestResult<String> =
        withContext(dispatchers.default()) {
            wrapResult {
                api.auth(
                    AuthRequest(
                        email,
                        password
                    )
                ).token
            }
        }
}