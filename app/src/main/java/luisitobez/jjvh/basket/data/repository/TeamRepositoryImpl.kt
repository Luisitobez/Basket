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

    override suspend fun updateTeam(team: TeamModel): Boolean {
        return try {
            val teamEntity = team.toEntity()
            val rowsUpdated = teamDao.update(teamEntity.id, teamEntity.name, teamEntity.shortName ?: "", teamEntity.logoUri ?: "")
            rowsUpdated > 0
        } catch (e: Exception) {
            throw Exception("Failed to update team")
        }
    }

    override suspend fun deleteTeam(team: TeamModel): Boolean {
        return try {
            val rowsDeleted = teamDao.delete(team.toEntity())
            rowsDeleted > 0
        } catch (e: Exception) {
            throw Exception("Failed to delete team", e)
        }
    }

    override suspend fun getTeamsMap(): Flow<Map<Int, String>> {
        return teamDao.observeAll().map { entities ->
            entities.map { entity ->
                entity.id.toInt() to entity.name
            }.toMap()
        }
    }
}