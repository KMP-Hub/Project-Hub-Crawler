package dev.kmphub.plugins

import dev.kmphub.modules.dataModule
import dev.kmphub.modules.repositoryModule
import dev.kmphub.modules.useCaseModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(dataModule, repositoryModule, useCaseModule)
    }
}