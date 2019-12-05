package com.example.atlanttest.di

import com.example.i_auth.api.AuthApi
import com.example.i_session.api.SessionApi
import com.example.i_user.api.UserApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single {
        get<Retrofit>().create(AuthApi::class.java)
    }
    single {
        get<Retrofit>().create(SessionApi::class.java)
    }
    single {
        get<Retrofit>().create(UserApi::class.java)
    }
}