package com.example.atlanttest.di

import com.example.i_session.SessionInteractor
import com.example.i_session.interceptors.TokenRefreshHelper
import com.example.i_session.repository.SessionRepository
import com.example.i_session.repository.SessionRepositoryImpl
import com.example.i_session.token_parser.TokenParser
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sessionModule = module {

    single { TokenRefreshHelper(get(), get()) }
    single { TokenParser(get()) }
    single<SessionRepository> { SessionRepositoryImpl(get(), get()) }
    single { SessionInteractor(get(named(STORAGE_TOKEN)), get(), get()) }
}