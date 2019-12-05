package com.example.atlanttest.presentation.features.auth

import com.example.i_auth.AuthInteractor
import com.example.i_auth.repository.AuthRepository
import com.example.i_auth.repository.AuthRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    viewModel { AuthVM(get()) }

    factory<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { AuthInteractor(get(), get(), get()) }
}