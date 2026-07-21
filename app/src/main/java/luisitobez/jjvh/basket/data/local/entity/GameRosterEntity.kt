package luisitobez.jjvh.basket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_roster",
    foreignKeys = [
        ForeignKey(entity = GameEntity::class, parentColumns = ["id"], childColumns = ["game_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = TeamEntity::class, parentColumns = ["id"], childColumns = ["team_id"])
    ],
    indices = [
        Index("game_id"), Index("team_id"),
        Index(value = ["game_id", "team_id", "player_name"], unique = true),
        Index(value = ["game_id", "team_id", "jersey_number"], unique = true)
    ]
)
data class GameRosterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "game_id") val gameId: Long,
    @ColumnInfo(name = "team_id") val teamId: Long,
    @ColumnInfo(name = "player_name") val playerName: String,
    @ColumnInfo(name = "jersey_number") val jerseyNumber: Int,
    @ColumnInfo(name = "is_starter") val isStarter: Boolean = false,
    @ColumnInfo(name = "is_captain") val isCaptain: Boolean = false,
    @ColumnInfo(name = "is_active") val isActive: Boolean = true
)