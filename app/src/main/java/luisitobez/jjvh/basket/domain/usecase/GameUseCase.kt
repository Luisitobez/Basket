package luisitobez.jjvh.basket.domain.usecase

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import luisitobez.jjvh.basket.domain.model.GameModel
import luisitobez.jjvh.basket.domain.repository.GameRepository

class GameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    fun getGameById(id: Int): Flow<GameModel?> {
        return gameRepository.getGameById(id)
    }

    fun getGames(): Flow<List<GameModel>> {
        return gameRepository.getGames()
    }

    suspend fun putGame(game: GameModel): PutGameResult {
        if (game.homeTeamId == game.awayTeamId) return PutGameResult.Error("No pueden ser el mismo equipo")
        if (game.gameDate.isNullOrBlank()) return PutGameResult.Error("Fecha requerida")

        val validStatus = setOf("SCHEDULED", "IN_PROGRESS", "FINISHED", "CANCELLED")
        if (game.status !in validStatus) return PutGameResult.Error("Estado inválido")

        if (game.status == "IN_PROGRESS" && (game.currentPeriod ?: 0) <= 0) {
            return PutGameResult.Error("Periodo actual inválido para juego en progreso")
        }

        gameRepository.putGame(game)
        return PutGameResult.Success
    }

    suspend fun updateGame(game: GameModel) {

        val existingGame = getGameById(game.id.toInt()).first()

        if (existingGame == null) {
            throw IllegalArgumentException(
                "El juego con ID ${game.id} no existe"
            )
        }

        if (game != existingGame) {
            gameRepository.updateGame(game)
        }
    }

    fun getGamesByTeamId(teamId: Int): Flow<List<GameModel>> {
        return gameRepository.getGamesByTeamId(teamId)
    }


    suspend fun deleteGame(game: GameModel): Boolean {
        return gameRepository.deleteGame(game)
    }

    suspend fun updateGameClock(
        gameId: Long,
        period: Int,
        secondsRemaining: Int,
        clockStartedAtEpochMs: Long?,
        status: String = "IN_PROGRESS"
    ) {
        require(gameId > 0 && period > 0 && secondsRemaining >= 0) { "Estado del reloj inválido" }
        gameRepository.updateGameClock(gameId, period, secondsRemaining, clockStartedAtEpochMs, status)
    }

    fun checkPeriod(
        period: Int,
        isClockRunning: Boolean,
        clockSecondsRemaining: Int,
        isTied: Boolean = false
    ): Boolean {
        // 1. El reloj siempre debe estar detenido y en 0
        if (isClockRunning || clockSecondsRemaining != 0) return false

        // 2. Períodos reglamentarios (1..4) → siempre permitidos
        if (period in 1..4) return true

        // 3. Prórrogas (5+) → solo si los equipos van empatados
        return isTied
    }

    fun startGame(roostersHomeTeam: Int, rostersAwayTeam: Int): Boolean {
        if (roostersHomeTeam < 5 || rostersAwayTeam < 5) {
            return false
        }else{
            return true
        }
    }
}

sealed class PutGameResult {
    object Success : PutGameResult()
    data class Error(val message: String) : PutGameResult()
}
