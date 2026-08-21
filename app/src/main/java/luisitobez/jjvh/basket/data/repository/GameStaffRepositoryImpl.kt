package luisitobez.jjvh.basket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import luisitobez.jjvh.basket.data.Mapper.toDomain
import luisitobez.jjvh.basket.data.Mapper.toEntity
import luisitobez.jjvh.basket.data.local.dao.GameStaffDao
import luisitobez.jjvh.basket.domain.model.GameStaffModel
import luisitobez.jjvh.basket.domain.repository.GameStaffRepository
import javax.inject.Inject

class GameStaffRepositoryImpl @Inject constructor(
    private val gameStaffDao: GameStaffDao
) : GameStaffRepository {
    override suspend fun insertAll(staff: List<GameStaffModel>) = gameStaffDao.insertAll(staff.map { it.toEntity() })
    override fun observeForGame(gameId: Long): Flow<List<GameStaffModel>> =
        gameStaffDao.observeForGame(gameId).map { staff -> staff.map { it.toDomain() } }
}
