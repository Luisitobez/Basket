package luisitobez.jjvh.basket.di

import android.app.Application
import androidx.room.Room
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
        ).build()
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
}