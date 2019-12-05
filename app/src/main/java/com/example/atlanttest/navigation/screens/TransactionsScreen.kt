package com.example.atlanttest.navigation.screens

import androidx.fragment.app.Fragment
import com.example.atlanttest.presentation.features.transactions.TransactionsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TransactionsScreen : SupportAppScreen() {

    override fun getScreenKey(): String = TransactionsFragment.TAG

    override fun getFragment(): Fragment =
        TransactionsFragment()
}