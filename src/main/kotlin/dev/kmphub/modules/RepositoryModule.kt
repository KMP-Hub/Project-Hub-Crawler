package dev.kmphub.modules

import dev.kmphub.domain.repositories.DataRepository
import dev.kmphub.domain.repositories.ResponseRepository
import dev.kmphub.repositories.DataRepositoryImpl
import dev.kmphub.repositories.ResponseRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<ResponseRepository> { ResponseRepositoryImpl(get()) }
    factory<DataRepository> { DataRepositoryImpl(get(), get()) }
}