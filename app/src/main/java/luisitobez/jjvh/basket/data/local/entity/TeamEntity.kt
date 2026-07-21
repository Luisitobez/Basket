package luisitobez.jjvh.basket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "team")
data class TeamEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "short_name") val shortName: String? = null,
    @ColumnInfo(name = "logo_uri") val logoUri: String? = null
)