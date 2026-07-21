package luisitobez.jjvh.basket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_event",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = TeamEntity::class, parentColumns = ["id"], childColumns = ["team_id"]),
        ForeignKey(entity = GameRosterEntity::class, parentColumns = ["id"], childColumns = ["roster_id"]),
        ForeignKey(entity = GameRosterEntity::class, parentColumns = ["id"], childColumns = ["related_roster_id"])
    ],
    indices = [
        Index("game_id"), Index("team_id"), Index("roster_id"), Index("related_roster_id"),
        Index(value = ["game_id", "sequence_number"], unique = true)
    ]
)
data class GameEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "game_id") val gameId: Long,
    @ColumnInfo(name = "sequence_number") val sequenceNumber: Int,
    @ColumnInfo(name = "period_number") val periodNumber: Int,
    @ColumnInfo(name = "clock_seconds_remaining") val clockSecondsRemaining: Int? = null,
    @ColumnInfo(name = "event_type") val eventType: String,
    @ColumnInfo(name = "team_id") val teamId: Long? = null,
    @ColumnInfo(name = "roster_id") val rosterId: Long? = null,
    @ColumnInfo(name = "related_roster_id") val relatedRosterId: Long? = null,
    val points: Int = 0,
    @ColumnInfo(name = "foul_type") val foulType: String? = null,
    @ColumnInfo(name = "is_cancelled") val isCancelled: Boolean = false,
    val note: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: String
)