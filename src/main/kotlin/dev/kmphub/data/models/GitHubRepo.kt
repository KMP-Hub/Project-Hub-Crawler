package dev.kmphub.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GitHubRepo(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("html_url")
    val url: String,
    @SerialName("topics")
    val tags: List<String>,
    @SerialName("stargazers_count")
    val starCount: Int,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("created_at")
    val createdAt: String,
)