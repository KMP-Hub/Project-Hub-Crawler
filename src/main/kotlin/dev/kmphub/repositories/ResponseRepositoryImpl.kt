package dev.kmphub.repositories

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import dev.kmphub.domain.entities.ProjectType
import dev.kmphub.domain.entities.FormResponse
import dev.kmphub.domain.entities.SupportedPlatform
import dev.kmphub.domain.repositories.ResponseRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ResponseRepositoryImpl(private val client: HttpClient) : ResponseRepository {
    override suspend fun fetchContent(): String =
        client.get(FORM_CSV_URL).bodyAsText()

    override suspend fun parseContent(content: String) :List<FormResponse> =
        csvReader().readAll(content).drop(1) // drop the header
            .mapNotNull { row ->
                val url = Url(row[1])
                if (isValidGitHubUrl(url).not()) return@mapNotNull null
                FormResponse(
                    repositoryName = extractRepoName(url),
                    supportedPlatforms = row[2].split(",").map { SupportedPlatform.valueOf(it.trim()) },
                    type = ProjectType.valueOf(row[3])
                )
            }.distinctBy { it.repositoryName } // prevent duplicates

    override fun isValidGitHubUrl(url: Url): Boolean =
        url.host == GITHUB_HOST && url.pathSegments.filter { it.isNotEmpty() }.size == 2

    private fun extractRepoName(url: Url): String =
        url.pathSegments.filter { it.isNotEmpty() }
            .let { result -> "${result[0]}/${result[1]}" }

    companion object {
        const val GITHUB_HOST = "github.com"
        const val FORM_CSV_URL =
            "https://docs.google.com/spreadsheets/d/e/2PACX-1vR23rS1fYdJgr9QeZNmVmuNTxnEWf0QX7CkRvGisRe-pdf03IDvbjdwynO06cpzYq8LuDcmPKpzTA3B/pub?output=csv"
    }
}