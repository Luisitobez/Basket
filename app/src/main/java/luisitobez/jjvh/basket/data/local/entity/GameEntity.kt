package luisitobez.jjvh.basket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game",
    foreignKeys = [
        ForeignKey(entity = CompetitionEntity::class, parentColumns = ["id"], childColumns = ["competition_id"], onDelete = ForeignKey.SET_NULL),
        ForeignKey(entity = TeamEntity::class, parentColumns = ["id"], childColumns = ["home_team_id"]),
        ForeignKey(entity = TeamEntity::class, parentColumns = ["id"], childColumns = ["away_team_id"])
    ],
    indices = [Index("competition_id"), Index("home_team_id"), Index("away_team_id")]
)
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "competition_id") val competitionId: Long? = null,
    @ColumnInfo(name = "home_team_id") val homeTeamId: Long,
    @ColumnInfo(name = "away_team_id") val awayTeamId: Long,
    val venue: String? = null,
    @ColumnInfo(name = "game_date") val gameDate: String, // ISO-8601: 2026-07-18T20:00:00
    val status: String = "SCHEDULED", // SCHEDULED, IN_PROGRESS, FINISHED, CANCELLED
    @ColumnInfo(name = "current_period") val currentPeriod: Int = 1,
    @ColumnInfo(name = "clock_seconds_remaining") val clockSecondsRemaining: Int? = null,
    val notes: String? = null
)