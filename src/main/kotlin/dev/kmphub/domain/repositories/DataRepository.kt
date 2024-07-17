package dev.kmphub.domain.repositories

import dev.kmphub.domain.entities.Project
import dev.kmphub.domain.entities.FormResponse

interface DataRepository {
    suspend fun fetchProjects(responses: List<FormResponse>): List<Project>
    fun saveProjects(projects: List<Project>)
}