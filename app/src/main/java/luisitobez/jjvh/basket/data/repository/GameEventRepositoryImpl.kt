package luisitobez.jjvh.basket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import luisitobez.jjvh.basket.data.Mapper.toDomain
import luisitobez.jjvh.basket.data.Mapper.toEntity
import luisitobez.jjvh.basket.data.local.dao.GameEventDao
import luisitobez.jjvh.basket.data.local.dao.MatchActionDao
import luisitobez.jjvh.basket.data.local.dao.PlayerGameStats
import luisitobez.jjvh.basket.data.local.entity.GameEventEntity
import luisitobez.jjvh.basket.data.local.entity.GameScoreView
import luisitobez.jjvh.basket.domain.model.GameEventModel
import luisitobez.jjvh.basket.domain.model.GameScoreModel
import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel
import luisitobez.jjvh.basket.domain.repository.GameEventRepository
import javax.inject.Inject

class GameEventRepositoryImpl @Inject constructor(
    private val gameEventDao: GameEventDao,
    private val matchActionDao: MatchActionDao
) : GameEventRepository {
    override suspend fun insert(event: GameEventEntity): Long {
        return matchActionDao.record(event)
    }

    override suspend fun getNextSequence(gameId: Long): Int {
        return gameEventDao.getNextSequence(gameId)
    }

    override fun observeEvents(gameId: Long): Flow<List<GameEventModel>> {
        return gameEventDao.observeEvents(gameId).map { events ->
            events.map { it.toDomain() }
        }
    }

    override suspend fun cancel(eventId: Long) {
        gameEventDao.cancel(eventId)
    }

    override suspend fun recordFoul(event: GameEventEntity): Long = matchActionDao.recordFoul(event)

    override suspend fun recordTechnicalFoul(event: GameEventEntity): Long =
        matchActionDao.recordTechnicalFoul(event)

    override fun observeScore(gameId: Long): Flow<GameScoreModel> {
        return gameEventDao.observeScore(gameId).map { it?.toDomain() ?: GameScoreModel(gameId, 0, 0) }
    }

    override fun observePlayerStats(gameId: Long): Flow<List<PlayerGameStatsModel>> {
        return gameEventDao.observePlayerStats(gameId).map { stats ->
            stats.map { it.toDomain() }
        }
    }
}
