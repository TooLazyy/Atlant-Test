package com.example.atlanttest.di

import com.example.base.network.BASE_URL
import com.example.i_session.interceptors.TokenInterceptor
import com.example.i_session.interceptors.TokenRefreshInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val REST_CLIENT = "rest_client"
const val WEB_SOCKET_CLIENT = "web_socket_client"

private const val READ_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L
private const val CONNECT_TIMEOUT = 30L

val networkModule = module {

    single<Gson> { GsonBuilder().create() }

    single { TokenRefreshInterceptor(get(named(STORAGE_TOKEN)), get()) }
    single { TokenInterceptor(get(named(STORAGE_TOKEN))) }
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

    single(named(REST_CLIENT)) { provideOkHttpClient(get(), get(), get()) }
    single(named(WEB_SOCKET_CLIENT)) { provideSocketOkHttpClient() }

    single { provideRetrofit(get(named(REST_CLIENT))) }
}

private fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    tokenRefreshInterceptor: TokenRefreshInterceptor,
    tokenInterceptor: TokenInterceptor
): OkHttpClient =
    OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(loggingInterceptor)
        addInterceptor(tokenInterceptor)
        addInterceptor(tokenRefreshInterceptor)
    }
        .build()

private fun provideSocketOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
    }
        .build()

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}