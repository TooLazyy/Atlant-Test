package com.example.atlanttest.presentation.base

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atlanttest.extensions.Event
import com.example.atlanttest.navigation.GlobalRouter
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseVM<S : BaseViewState> : ViewModel(), KoinComponent {

    abstract val state: S

    protected val router: GlobalRouter by inject()

    protected val stateLiveData = MutableLiveData<S>()
    val stateData: LiveData<S>
        get() = stateLiveData

    protected val errorLiveData = MutableLiveData<Event<Throwable>>()
    val errorData: LiveData<Event<Throwable>>
        get() = errorLiveData

    @MainThread
    protected fun render(state: S) {
        stateLiveData.value = state
    }

    @MainThread
    protected fun onError(error: Throwable) {
        state.loading = LoadingState.None
        errorLiveData.value = Event(error)
        render(state)
    }

    fun exit() {
        router.exit()
    }
}