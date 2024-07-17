package dev.kmphub.modules

import dev.kmphub.domain.interactors.UpdateProjects
import org.koin.dsl.module

val useCaseModule = module {
    factory { UpdateProjects(get(), get()) }
}