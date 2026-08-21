package luisitobez.jjvh.basket.domain.repository

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.data.local.dao.PlayerGameStats
import luisitobez.jjvh.basket.data.local.entity.GameEventEntity
import luisitobez.jjvh.basket.data.local.entity.GameScoreView
import luisitobez.jjvh.basket.domain.model.GameEventModel
import luisitobez.jjvh.basket.domain.model.GameScoreModel
import luisitobez.jjvh.basket.domain.model.PlayerGameStatsModel

interface GameEventRepository {
    suspend fun insert(event: GameEventEntity): Long
    suspend fun getNextSequence(gameId: Long): Int
    fun observeEvents(gameId: Long): Flow<List<GameEventModel>>
    suspend fun cancel(eventId: Long)
    suspend fun recordFoul(event: GameEventEntity): Long
    fun observeScore(gameId: Long): Flow<GameScoreModel>
    fun observePlayerStats(gameId: Long): Flow<List<PlayerGameStatsModel>>

}
