package com.example.i_user.repository

import com.example.base.RequestResult
import com.example.base.dispatchers.DispatchersProvider
import com.example.base.wrapResult
import com.example.domain.UserProfile
import com.example.i_user.api.UserApi
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val dispatchers: DispatchersProvider,
    private val api: UserApi
) : UserRepository {

    override suspend fun getCurrentUserProfile(): RequestResult<UserProfile> =
        withContext(dispatchers.default()) {
            wrapResult {
                api.getUserProfile().transform()
            }
        }
}