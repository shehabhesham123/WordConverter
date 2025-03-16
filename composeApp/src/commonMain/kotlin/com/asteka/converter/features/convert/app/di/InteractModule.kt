package com.asteka.converter.features.convert.app.di

import com.asteka.converter.features.convert.domain.interactor.GetWordDocLocally
import org.koin.dsl.module

val InteractModule = module {
    single<GetWordDocLocally> { GetWordDocLocally(get()) }
}