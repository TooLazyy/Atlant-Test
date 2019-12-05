package com.example.atlanttest.presentation.features.transactions

import androidx.lifecycle.viewModelScope
import com.example.atlanttest.navigation.screens.AuthScreen
import com.example.atlanttest.presentation.base.BaseVM
import com.example.atlanttest.presentation.base.LoadingState
import com.example.base.RequestResult
import com.example.i_session.SessionInteractor
import com.example.i_transactions.TransactionsInteractor
import com.example.i_user.UserInteractor
import com.example.i_web_socket.WebSocketSource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class TransactionsVM(
    private val userInteractor: UserInteractor,
    private val sessionInteractor: SessionInteractor,
    private val transactionsInteractor: TransactionsInteractor,
    private val webSocketSource: WebSocketSource
) : BaseVM<TransactionsState>() {

    override val state: TransactionsState = TransactionsState()

    private var transactionsJob: Job? = null

    init {
        subscribeToSocketState()
        subscribeToTransactions()
    }

    fun onExitClick() {
        state.loading = LoadingState.Loading
        render(state)

        viewModelScope.launch {
            transactionsInteractor.unsubscribeTransaction()
            sessionInteractor.logout()
            router.newRootScreen(AuthScreen())
        }
    }

    fun onDisposeClicked() {
        transactionsInteractor.unsubscribeTransaction()
        state.transactions.clear()
        render(state)
    }


    fun onStopClicked() {
        transactionsInteractor.unsubscribeTransaction()
    }

    fun onStartClicked() {
        subscribeToTransactions()
    }

    fun loadData() {
        if (!state.hasProfileInfo) {
            state.loading = LoadingState.Loading
            render(state)
        }

        viewModelScope.launch {
            when (val result = userInteractor.getCurrentUser()) {
                is RequestResult.Success -> {
                    state.userProfile = result.data
                    state.loading = LoadingState.None
                    render(state)
                }
                is RequestResult.Error -> onError(result.error)
            }
        }
    }

    private fun subscribeToSocketState() {
        viewModelScope.launch {
            webSocketSource
                .subscribeToSocketState()
                .consumeAsFlow()
                .collect {
                    state.socketState = it
                    render(state)
                }
        }
    }

    private fun subscribeToTransactions() {
        transactionsJob?.cancel()

        transactionsJob = viewModelScope.launch {
            transactionsInteractor
                .getTransactions()
                .collect {
                    state.transactions.add(it)
                    render(state)
                }
        }
    }
}