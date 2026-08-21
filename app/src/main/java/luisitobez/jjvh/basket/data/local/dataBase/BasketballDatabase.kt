package luisitobez.jjvh.basket.data.local.dataBase

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import luisitobez.jjvh.basket.data.local.dao.CompetitionDao
import luisitobez.jjvh.basket.data.local.dao.GameDao
import luisitobez.jjvh.basket.data.local.dao.GameEventDao
import luisitobez.jjvh.basket.data.local.dao.GameRosterDao
import luisitobez.jjvh.basket.data.local.dao.GameStaffDao
import luisitobez.jjvh.basket.data.local.dao.MatchActionDao
import luisitobez.jjvh.basket.data.local.dao.TeamDao
import luisitobez.jjvh.basket.data.local.dao.TeamPeriodFoulDao
import luisitobez.jjvh.basket.data.local.entity.CompetitionEntity
import luisitobez.jjvh.basket.data.local.entity.GameEntity
import luisitobez.jjvh.basket.data.local.entity.GameEventEntity
import luisitobez.jjvh.basket.data.local.entity.GameRosterEntity
import luisitobez.jjvh.basket.data.local.entity.GameScoreView
import luisitobez.jjvh.basket.data.local.entity.GameStaffEntity
import luisitobez.jjvh.basket.data.local.entity.TeamEntity
import luisitobez.jjvh.basket.data.local.entity.TeamPeriodFoulEntity


@Database(
    entities = [
        CompetitionEntity::class,
        TeamEntity::class,
        GameEntity::class,
        GameRosterEntity::class,
        GameStaffEntity::class,
        TeamPeriodFoulEntity::class,
        GameEventEntity::class
    ],
    views = [GameScoreView::class],
    version = 2,
    exportSchema = true
)
abstract class BasketballDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun competitionDao(): CompetitionDao
    abstract fun gameDao(): GameDao
    abstract fun gameRosterDao(): GameRosterDao
    abstract fun gameStaffDao(): GameStaffDao
    abstract fun teamPeriodFoulDao(): TeamPeriodFoulDao
    abstract fun gameEventDao(): GameEventDao
    abstract fun matchActionDao(): MatchActionDao

    companion object {
        @Volatile
        private var INSTANCE: BasketballDatabase? = null

        fun getDatabase(context: Context): BasketballDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BasketballDatabase::class.java,
                    "basketball.db"
                ).build()
                INSTANCE = instance
                instance
            }
    }
}


