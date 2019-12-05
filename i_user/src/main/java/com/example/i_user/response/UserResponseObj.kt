package com.example.i_user.response

import com.example.base.network.Transformable
import com.example.base.network.transform
import com.example.domain.AccountInfo
import com.example.domain.UserProfile
import com.example.domain.UserProfileInfo
import com.google.gson.annotations.SerializedName

data class UserResponseObj(
    @SerializedName("info") val info: UserInfoObj?
) : Transformable<UserProfile> {

    override fun transform(): UserProfile =
        UserProfile(
            info?.transform() ?: AccountInfo("", false, ""),
            info?.profiles?.transform() ?: listOf()
        )
}

data class UserInfoObj(
    @SerializedName("account") val account: UserAccountObj?,
    @SerializedName("profiles") val profiles: List<UserProfileObj>?
) : Transformable<AccountInfo> {

    override fun transform(): AccountInfo =
        AccountInfo(
            account?.email ?: "",
            account?.emailVerified ?: false,
            account?.phone ?: ""
        )
}

data class UserAccountObj(
    @SerializedName("email") val email: String?,
    @SerializedName("email_verified") val emailVerified: Boolean?,
    @SerializedName("phone") val phone: String?
)

data class UserProfileObj(
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?
) : Transformable<UserProfileInfo> {

    override fun transform(): UserProfileInfo =
        UserProfileInfo(
            firstName ?: "",
            lastName ?: ""
        )
}