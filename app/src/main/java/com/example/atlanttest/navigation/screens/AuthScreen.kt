package com.example.atlanttest.navigation.screens

import androidx.fragment.app.Fragment
import com.example.atlanttest.presentation.features.auth.AuthFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class AuthScreen : SupportAppScreen() {

    override fun getScreenKey(): String = AuthFragment.TAG

    override fun getFragment(): Fragment =
        AuthFragment()
}