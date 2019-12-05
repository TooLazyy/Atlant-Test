package com.example.i_user.repository

import com.example.base.RequestResult
import com.example.domain.UserProfile

interface UserRepository {

    suspend fun getCurrentUserProfile(): RequestResult<UserProfile>
}