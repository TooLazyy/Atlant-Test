package com.example.atlanttest.presentation.features.transactions

import com.example.i_transactions.TransactionsInteractor
import com.example.i_transactions.repository.TransactionsRepository
import com.example.i_transactions.repository.TransactionsRepositoryImpl
import com.example.i_user.UserInteractor
import com.example.i_user.repository.UserRepository
import com.example.i_user.repository.UserRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionsModule = module {

    viewModel { TransactionsVM(get(), get(), get(), get()) }

    factory<UserRepository> { UserRepositoryImpl(get(), get()) }
    factory { UserInteractor(get()) }

    factory<TransactionsRepository> { TransactionsRepositoryImpl(get()) }
    factory { TransactionsInteractor(get()) }
}