package luisitobez.jjvh.basket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import luisitobez.jjvh.basket.data.Mapper.toDomain
import luisitobez.jjvh.basket.data.Mapper.toEntity
import luisitobez.jjvh.basket.data.local.dao.GameDao
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameDao: GameDao
) : GameRepository {
    override fun getGameById(id: Int): Flow<GameModel> {
        try {
            return gameDao.observeById(id.toLong())
                .map { it?.toDomain() ?: throw Exception("Game not found") }
        } catch (e: Exception) {
            throw Exception("Game not found")
        }
    }

    override fun getGames(): Flow<List<GameModel>> {
        try {
            return gameDao.observeAll().map { entities ->
                entities.map { entity ->
                    entity.toDomain()
                }
            }
        } catch (e: Exception) {
            throw Exception("Games not found")
        }
    }

    override suspend fun putGame(game: GameModel): Flow<GameModel> {
        return try {
            val gameEntity = game.toEntity()
            val gameId = gameDao.insert(gameEntity)
            getGameById(gameId.toInt())
        } catch (e: Exception) {
            throw Exception("Failed to insert game")
        }
    }

    override fun getGamesByTeamId(teamId: Int): Flow<List<GameModel>> {
        try {
            return gameDao.observeByTeamId(teamId.toLong()).map { entities ->
                entities.map { entity ->
                    entity.toDomain()
                }
            }
        } catch (e: Exception) {
            throw Exception("Games not found")
        }
    }

    override suspend fun deleteGame(game: GameModel): Boolean {
        return try {
            val rowsDeleted = gameDao.delete(game.toEntity())
            rowsDeleted > 0
        } catch (e: Exception) {
            throw Exception("Failed to delete game", e)
        }
    }

    override suspend fun updateGameClock(
        gameId: Long,
        period: Int,
        secondsRemaining: Int,
        clockStartedAtEpochMs: Long?,
        status: String
    ) {
        gameDao.updateGameClock(gameId, period, secondsRemaining, clockStartedAtEpochMs, status)
    }

    override suspend fun updateGame(game: GameModel) {
        gameDao.update(game.toEntity())
    }
}
