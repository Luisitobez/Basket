package luisitobez.jjvh.basket.domain.repository

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.GameRosterModel

interface GameRosterRepository {
    suspend fun insert(player: GameRosterModel): Long
    suspend fun insertAll(players: List<GameRosterModel>)
    suspend fun update(player: GameRosterModel)
    fun observeTeamRoster(gameId: Long, teamId: Long): Flow<List<GameRosterModel>>
    suspend fun getById(rosterId: Long): GameRosterModel?
    suspend fun setActive(rosterId: Long, isActive: Boolean)
    suspend fun delete(rosterId: Long)
    suspend fun getAllOfGame(gameId: Long): Flow<List<GameRosterModel>>
}