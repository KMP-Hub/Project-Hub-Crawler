package dev.kmphub.plugins

import dev.kmphub.domain.interactors.UpdateProjects
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.FileReader

fun Application.configureRouting() {
    val updateProjects by inject<UpdateProjects>()
    routing {
        get("/") {
            updateProjects()
            call.respondText(FileReader("projects.json").readText())
        }
    }
}