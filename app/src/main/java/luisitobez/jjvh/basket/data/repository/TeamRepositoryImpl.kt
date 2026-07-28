package luisitobez.jjvh.basket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import luisitobez.jjvh.basket.data.Mapper.toDomain
import luisitobez.jjvh.basket.data.Mapper.toEntity
import luisitobez.jjvh.basket.data.local.dao.TeamDao
import luisitobez.jjvh.basket.domain.model.TeamModel
import luisitobez.jjvh.basket.domain.repository.TeamRepository
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamDao: TeamDao
) : TeamRepository {
    override suspend fun getTeamById(id: Int): TeamModel {
        return teamDao.get(id.toLong())?.toDomain() ?: throw Exception("Team not found")
    }

    override fun getTeams(): Flow<List<TeamModel>> {
        return teamDao.observeAll().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun putTeam(team: TeamModel): TeamModel {
        return try {
            val teamEntity = team.toEntity()
            val teamId = teamDao.insert(teamEntity)
            getTeamById(teamId.toInt())
        } catch (e: Exception) {
            throw Exception("Failed to insert team")
        }

    }

}