package luisitobez.jjvh.basket.domain.repository

import kotlinx.coroutines.flow.Flow
import luisitobez.jjvh.basket.domain.model.GameStaffModel

interface GameStaffRepository {
    suspend fun insertAll(staff: List<GameStaffModel>)
    fun observeForGame(gameId: Long): Flow<List<GameStaffModel>>
}
