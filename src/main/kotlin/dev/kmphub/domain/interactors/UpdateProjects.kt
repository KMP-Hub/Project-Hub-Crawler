package dev.kmphub.domain.interactors

import dev.kmphub.domain.repositories.DataRepository
import dev.kmphub.domain.repositories.ResponseRepository

class UpdateProjects(private val responseRepository: ResponseRepository, private val dataRepository: DataRepository) {
    suspend operator fun invoke() {
        val content = responseRepository.fetchContent()
        val responses = responseRepository.parseContent(content)
        val projects = dataRepository.fetchProjects(responses)
        dataRepository.saveProjects(projects)
    }
}