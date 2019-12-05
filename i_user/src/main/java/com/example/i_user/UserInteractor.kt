package com.example.i_user

import com.example.base.RequestResult
import com.example.domain.UserProfile
import com.example.i_user.repository.UserRepository

class UserInteractor(
    private val repository: UserRepository
) {

    suspend fun getCurrentUser(): RequestResult<UserProfile> =
        repository.getCurrentUserProfile()
}