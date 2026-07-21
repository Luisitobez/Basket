package luisitobez.jjvh.basket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_staff",
    foreignKeys = [
        ForeignKey(entity = GameEntity::class, parentColumns = ["id"], childColumns = ["game_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = TeamEntity::class, parentColumns = ["id"], childColumns = ["team_id"])
    ],
    indices = [Index("game_id"), Index("team_id")]
)
data class GameStaffEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "game_id") val gameId: Long,
    @ColumnInfo(name = "team_id") val teamId: Long? = null,
    val role: String, // HEAD_COACH, ASSISTANT_COACH, REFEREE, SCORER, TIMEKEEPER...
    @ColumnInfo(name = "full_name") val fullName: String
)