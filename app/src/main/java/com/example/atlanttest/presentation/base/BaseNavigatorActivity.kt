package com.example.atlanttest.presentation.base

import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

abstract class BaseNavigatorActivity : AppCompatActivity() {

    abstract val containerId: Int

    private val navigationHolder: NavigatorHolder by inject()

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(this, containerId) {}
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }
}