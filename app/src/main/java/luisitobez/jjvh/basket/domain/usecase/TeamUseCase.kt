package luisitobez.jjvh.basket.domain.usecase

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.domain.repository.TeamRepository
import javax.inject.Inject

class TeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository
) {
    suspend fun getTeamById(id: Int): TeamModel {
        return teamRepository.getTeamById(id)
    }

    fun getTeams(): Flow<List<TeamModel>> {
        return teamRepository.getTeams()
    }

    suspend fun putTeam(name: String, shortName: String, uri: String): Boolean {
        val team = TeamModel(
            id = 0,
            name = name,
            shortName = shortName,
            logoUri = uri
        )

        if (!(name.isBlank() || shortName.isBlank())) {
            teamRepository.putTeam(team)
            return true
        }

        return false
    }

    suspend fun updateTeam(id: Int, name: String, shortName: String, uri: String): Boolean {
        val team = TeamModel(
            id = id.toLong(),
            name = name,
            shortName = shortName,
            logoUri = uri
        )
        return teamRepository.updateTeam(team)
    }

    suspend fun getNameByTeamId(id: Int): String {
        return teamRepository.getTeamById(id).name
    }

    suspend fun deleteTeam(
        id: Int,
        name: String,
        shortName: String,
        uri: String
    ): Boolean {
        val team = TeamModel(
            id = id.toLong(),
            name = name,
            shortName = shortName,
            logoUri = uri
        )
        return teamRepository.deleteTeam(team)
    }
}