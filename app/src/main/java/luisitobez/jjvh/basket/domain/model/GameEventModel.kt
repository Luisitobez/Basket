package luisitobez.jjvh.basket.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class GameEventModel(
    val id: Long,
    val gameId: Long,
    val sequenceNumber: Int,
    val periodNumber: Int,
    val clockSecondsRemaining: Int?,
    val eventType: String,
    val teamId: Long?,
    val rosterId: Long?,
    val relatedRosterId: Long?,
    val points: Int,
    val foulType: String?,
    val isCancelled: Boolean,
    val note: String?,
    val createdAt: String
)