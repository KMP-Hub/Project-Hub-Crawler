package dev.kmphub.repositories

import dev.kmphub.data.models.GitHubRepo
import dev.kmphub.domain.entities.Project
import dev.kmphub.domain.entities.FormResponse
import dev.kmphub.domain.repositories.DataRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileWriter

class DataRepositoryImpl(private val client: HttpClient, private val json: Json) : DataRepository {
    override suspend fun fetchProjects(responses: List<FormResponse>): List<Project> = coroutineScope {
        responses.map { async { fetchRepo(it) } }.awaitAll().map { (repo, response) ->
            Project(
                id = repo.id,
                name = repo.name,
                description = repo.description,
                platforms = response.supportedPlatforms,
                types = listOf(response.type),
                url = repo.url,
                tags = repo.tags,
                starCount = repo.starCount,
                updatedAt = repo.updatedAt,
                createdAt = repo.createdAt
            )
        }
    }

    override fun saveProjects(projects: List<Project>) {
        // TODO: use database in the future
        try {
            FileWriter("projects.json").use { writer ->
                writer.write(json.encodeToString(projects))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun fetchRepo(response: FormResponse): Pair<GitHubRepo, FormResponse> {
        val repo = client.get {
            header(HttpHeaders.Authorization, "Bearer ${System.getenv("GITHUB_API_ACCESS_TOKEN")}")
            url("$GITHUB_API_URL/repos/${response.repositoryName}")
        }.bodyAsText().let { json.decodeFromString<GitHubRepo>(it) }


        return Pair(repo, response)
    }

    companion object {
        const val GITHUB_API_URL = "https://api.github.com"
    }
}