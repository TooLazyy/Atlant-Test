package com.example.domain

data class UserProfile(
    val accountInfo: AccountInfo,
    val profiles: List<UserProfileInfo>
) {

    val hasProfileInfo: Boolean
        get() = profiles.isNotEmpty()
}

data class AccountInfo(
    val email: String,
    val emailVerified: Boolean,
    val phone: String
)

data class UserProfileInfo(
    val firstName: String,
    val lastName: String
)