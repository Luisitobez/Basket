package luisitobez.jjvh.basket.domain.usecase

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.GameStaffModel
import luisitobez.jjvh.basket.domain.repository.GameStaffRepository
import javax.inject.Inject

class GameStaffUseCase @Inject constructor(private val repository: GameStaffRepository) {
    fun observeForGame(gameId: Long): Flow<List<GameStaffModel>> = repository.observeForGame(gameId)
    suspend fun saveAll(staff: List<GameStaffModel>) {
        require(staff.all { it.gameId > 0 && it.fullName.isNotBlank() && it.role.isNotBlank() }) { "Personal de partido inválido" }
        repository.insertAll(staff)
    }
}
