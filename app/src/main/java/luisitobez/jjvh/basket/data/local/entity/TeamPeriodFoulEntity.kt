package luisitobez.jjvh.basket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "team_period_foul",
    primaryKeys = ["game_id", "team_id", "period_number"],
    foreignKeys = [
        ForeignKey(entity = GameEntity::class, parentColumns = ["id"], childColumns = ["game_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = TeamEntity::class, parentColumns = ["id"], childColumns = ["team_id"])
    ],
    indices = [Index("team_id")]
)
data class TeamPeriodFoulEntity(
    @ColumnInfo(name = "game_id") val gameId: Long,
    @ColumnInfo(name = "team_id") val teamId: Long,
    @ColumnInfo(name = "period_number") val periodNumber: Int,
    @ColumnInfo(name = "foul_count") val foulCount: Int = 0
)