package dev.kmphub.domain.repositories

import dev.kmphub.domain.entities.FormResponse
import io.ktor.http.*

interface ResponseRepository {
    suspend fun fetchContent(): String
    suspend fun parseContent(content: String): List<FormResponse>
    fun isValidGitHubUrl(url: Url): Boolean
}