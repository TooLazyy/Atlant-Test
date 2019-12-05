package com.example.atlanttest.di

import com.example.base.dispatchers.DispatchersProvider
import com.example.base.dispatchers.DispatchersProviderImpl
import org.koin.dsl.module

val applicationModule = module {

    single<DispatchersProvider> { DispatchersProviderImpl() }
}