package com.example.atlanttest.presentation.features.auth

import android.util.Patterns
import com.example.atlanttest.presentation.base.BaseViewState

class AuthState : BaseViewState() {

    var email: String = ""
    var password: String = ""

    val isLoginEnabled: Boolean
        get() = password.trim().isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).find()
}