package luisitobez.jjvh.basket.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class GameRosterModel(
    val id: Long = 0,
    val gameId: Long,
    val teamId: Long,
    val playerName: String,
    val jerseyNumber: Int,
    val isStarter: Boolean = false,
    val isCaptain: Boolean = false,
    val isActive: Boolean = true
)