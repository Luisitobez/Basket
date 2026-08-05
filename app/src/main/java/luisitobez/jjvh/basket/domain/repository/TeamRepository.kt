package luisitobez.jjvh.basket.domain.repository

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.TeamModel

interface TeamRepository {
    suspend fun getTeamById(id: Int): TeamModel
    fun getTeams(): Flow<List<TeamModel>>
    suspend fun putTeam(team: TeamModel): TeamModel
    suspend fun updateTeam(team: TeamModel): Boolean
    suspend fun deleteTeam(team: TeamModel): Boolean
    suspend fun getTeamsMap(): Flow<Map<Int, String>>
}