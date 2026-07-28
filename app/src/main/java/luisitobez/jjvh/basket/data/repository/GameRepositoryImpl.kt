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
}