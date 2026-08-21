package luisitobez.jjvh.basket.domain.usecase

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.data.local.entity.GameRosterEntity
import luisitobez.jjvh.basket.domain.model.GameRosterModel
import luisitobez.jjvh.basket.domain.repository.GameRosterRepository
import javax.inject.Inject

class GameRosterUseCase @Inject constructor(
    private val gameRosterRepository: GameRosterRepository
) {
    fun observeTeamRoster(gameId: Long, teamId: Long): Flow<List<GameRosterModel>> {
        return gameRosterRepository.observeTeamRoster(gameId, teamId)
    }

    suspend fun agregarJugador(
        gameId: Long,
        teamId: Long,
        playerName: String,
        jerseyNumber: String
    ) {
        val roster = GameRosterModel(
            gameId = gameId,
            teamId = teamId,
            playerName = playerName,
            jerseyNumber = jerseyNumber.toInt()
        )
        gameRosterRepository.insert(roster)
    }

    suspend fun eliminarJugador(roster: Long) {
        gameRosterRepository.delete(roster)
    }

    suspend fun getAllOfGame(gameId: Long): Flow<List<GameRosterModel>> {
        return gameRosterRepository.getAllOfGame(gameId)
    }
}