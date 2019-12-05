package com.example.atlanttest.di

import com.example.atlanttest.navigation.GlobalRouter
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder

val navigationModule = module {

    single<Cicerone<GlobalRouter>> {
        Cicerone.create(GlobalRouter())
    }
    single<GlobalRouter> {
        val cicerone: Cicerone<GlobalRouter> = get()
        cicerone.router
    }
    single<NavigatorHolder> {
        val cicerone: Cicerone<GlobalRouter> = get()
        cicerone.navigatorHolder
    }
}