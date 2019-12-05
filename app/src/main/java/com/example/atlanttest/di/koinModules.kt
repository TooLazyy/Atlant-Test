package com.example.atlanttest.di

import com.example.atlanttest.presentation.features.auth.authModule
import com.example.atlanttest.presentation.features.main.mainModule
import com.example.atlanttest.presentation.features.transactions.transactionsModule

private val featureModules = listOf(
    mainModule,
    authModule,
    transactionsModule
)

private val coreModules = listOf(
    applicationModule,
    preferencesModule,
    navigationModule,
    networkModule,
    apiModule,
    sessionModule,
    socketModule
)

val koinModules = featureModules + coreModules