package com.example.atlanttest.presentation.features.main

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel { MainVM(get()) }
}