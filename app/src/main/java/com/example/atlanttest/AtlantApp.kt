package com.example.atlanttest

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.atlanttest.di.koinModules
import com.example.atlanttest.extensions.ApplicationLifecycleObserver
import com.example.i_web_socket.WebSocketSource
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AtlantApp : Application() {

    private val appObserver = ApplicationLifecycleObserver(::onAppPause, ::onAppResume)

    private val socketInteractor: WebSocketSource by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appObserver)
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(koinModules)
        }
    }

    private fun onAppPause() {
        socketInteractor.disconnectSilent()
    }

    private fun onAppResume() {
        socketInteractor.connect()
    }
}