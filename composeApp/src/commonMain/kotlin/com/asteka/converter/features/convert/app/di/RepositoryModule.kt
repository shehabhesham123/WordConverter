package com.asteka.converter.features.convert.app.di

import com.asteka.converter.features.convert.data.local.Local
import com.asteka.converter.features.convert.data.repository.RepositoryImpl
import com.asteka.converter.features.convert.domain.repository.Repository
import org.koin.dsl.module


val RepositoryModule = module {
    single { Local() }
    single<Repository> { RepositoryImpl(get()) }
}