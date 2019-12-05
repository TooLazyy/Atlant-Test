package com.example.atlanttest.presentation.features.auth

import androidx.lifecycle.viewModelScope
import com.example.atlanttest.extensions.JobDebounce
import com.example.atlanttest.navigation.screens.TransactionsScreen
import com.example.atlanttest.presentation.base.BaseVM
import com.example.atlanttest.presentation.base.LoadingState
import com.example.base.RequestResult
import com.example.i_auth.AuthInteractor
import kotlinx.coroutines.launch

private const val INPUT_DELAY = 300L

class AuthVM(
    private val authInteractor: AuthInteractor
) : BaseVM<AuthState>() {

    override val state: AuthState =
        AuthState()

    private val passwordJobDebounce = JobDebounce(INPUT_DELAY)
    private val emailJobDebounce = JobDebounce(INPUT_DELAY)

    fun onEmailChanged(email: String) {
        emailJobDebounce.debounceAction(viewModelScope) {
            state.email = email
            render(state)
        }
    }

    fun onPasswordChanged(password: String) {
        passwordJobDebounce.debounceAction(viewModelScope) {
            state.password = password
            render(state)
        }
    }

    fun onLoginClick() {
        state.loading = LoadingState.Loading
        render(state)

        viewModelScope.launch {
            when (val result = authInteractor.auth(state.email, state.password)) {
                is RequestResult.Success -> openMainScreen()
                is RequestResult.Error -> onError(result.error)
            }
        }
    }

    private fun openMainScreen() {
        router.newRootScreen(TransactionsScreen())
    }
}