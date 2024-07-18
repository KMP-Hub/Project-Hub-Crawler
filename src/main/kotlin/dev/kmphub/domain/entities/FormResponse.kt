package dev.kmphub.domain.entities

class FormResponse(
    val repositoryName: String,
    val supportedPlatforms: List<SupportedPlatform>,
    val type: ProjectType
)

enum class SupportedPlatform {
    Android, iOS, Desktop, Web
}

enum class ProjectType {
    Library, Showcase, Framework, Other
}