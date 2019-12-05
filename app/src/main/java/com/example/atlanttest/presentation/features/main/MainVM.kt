package com.example.atlanttest.presentation.features.main

import com.example.atlanttest.navigation.screens.AuthScreen
import com.example.atlanttest.navigation.screens.TransactionsScreen
import com.example.atlanttest.presentation.base.BaseVM
import com.example.i_session.SessionInteractor

class MainVM(
    private val sessionInteractor: SessionInteractor
) : BaseVM<MainViewState>() {

    override val state: MainViewState = MainViewState()

    fun openInitialScreen() {
        router.newRootScreen(
            if (sessionInteractor.isAuthorized) {
                TransactionsScreen()
            } else {
                AuthScreen()
            }
        )
    }
}