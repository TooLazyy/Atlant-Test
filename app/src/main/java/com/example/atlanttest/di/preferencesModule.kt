package com.example.atlanttest.di

import android.content.Context
import com.example.base.SimpleKeyValueStorage
import com.example.i_token.TokenData
import com.example.i_token.TokenStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val STORAGE_TOKEN = "token_storage"

private const val SHARED_PREFERENCES_STORAGE = "storage_shared_preferences"

val preferencesModule = module {

    single {
        androidContext()
            .getSharedPreferences(
                SHARED_PREFERENCES_STORAGE,
                Context.MODE_PRIVATE
            )
    }

    single<SimpleKeyValueStorage<TokenData>>(named(STORAGE_TOKEN)) { TokenStorage(get(), get()) }
}