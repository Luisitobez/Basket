package luisitobez.jjvh.basket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import luisitobez.jjvh.basket.data.Mapper.toDomain
import luisitobez.jjvh.basket.data.Mapper.toEntity
import luisitobez.jjvh.basket.data.local.dao.GameRosterDao
import luisitobez.jjvh.basket.data.local.entity.GameRosterEntity
import luisitobez.jjvh.basket.domain.model.GameRosterModel
import luisitobez.jjvh.basket.domain.repository.GameRosterRepository
import javax.inject.Inject

class GameRosterRepositoryImpl @Inject constructor(
    private val gameRosterDao: GameRosterDao
): GameRosterRepository {
    override suspend fun insert(player: GameRosterModel): Long {
        return gameRosterDao.insert(player.toEntity())
    }

    override suspend fun insertAll(players: List<GameRosterModel>) {
        gameRosterDao.insertAll(players.map { it.toEntity() })
    }

    override suspend fun update(player: GameRosterModel) {
        gameRosterDao.update(player.toEntity())
    }

    override fun observeTeamRoster(
        gameId: Long,
        teamId: Long
    ): Flow<List<GameRosterModel>> {
        return gameRosterDao.observeTeamRoster(gameId, teamId).map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun getById(rosterId: Long): GameRosterModel? {
        return gameRosterDao.getById(rosterId)?.toDomain()
    }

    override suspend fun setActive(rosterId: Long, isActive: Boolean) {
        gameRosterDao.setActive(rosterId, isActive)
    }

    override suspend fun delete(rosterId: Long) {
        gameRosterDao.delete(rosterId)
    }

    override suspend fun getAllOfGame(gameId: Long): Flow<List<GameRosterModel>> {
        return gameRosterDao.getAll(gameId).map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }
}