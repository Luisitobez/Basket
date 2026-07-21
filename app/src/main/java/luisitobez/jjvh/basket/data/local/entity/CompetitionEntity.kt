package luisitobez.jjvh.basket.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "competition")
data class CompetitionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String? = null,
    val ruleset: String = "FIBA",
    @ColumnInfo(name = "periods_count") val periodsCount: Int = 4,
    @ColumnInfo(name = "period_minutes") val periodMinutes: Int = 10,
    @ColumnInfo(name = "overtime_minutes") val overtimeMinutes: Int = 5
)