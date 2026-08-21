package luisitobez.jjvh.basket.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luisitobez.jjvh.basket.data.local.dataBase.BasketballDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): BasketballDatabase {

        return Room.databaseBuilder(
            application,
            BasketballDatabase::class.java,
            "basket.db"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    fun provideCompetitionDao(
        database: BasketballDatabase
    ) = database.competitionDao()

    @Provides
    fun provideGameDao(
        database: BasketballDatabase
    ) = database.gameDao()

    @Provides
    fun provideGameRosterDao(
        database: BasketballDatabase
    ) = database.gameRosterDao()

    @Provides
    fun provideGameStaffDao(
        database: BasketballDatabase
    ) = database.gameStaffDao()

    @Provides
    fun provideTeamPeriodFoulDao(
        database: BasketballDatabase
    ) = database.teamPeriodFoulDao()

    @Provides
    fun provideGameEventDao(
        database: BasketballDatabase
    ) = database.gameEventDao()

    @Provides
    fun provideMatchActionDao(
        database: BasketballDatabase
    ) = database.matchActionDao()

    @Provides
    fun provideTeamDao(
        database: BasketballDatabase
    ) = database.teamDao()

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "ALTER TABLE game ADD COLUMN clock_started_at_epoch_ms INTEGER"
            )
        }
    }
}
