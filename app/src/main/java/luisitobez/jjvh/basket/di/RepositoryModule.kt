package luisitobez.jjvh.basket.di

import dagger.Provides
import jakarta.inject.Singleton
import luisitobez.jjvh.basket.data.local.dao.CompetitionDao
import luisitobez.jjvh.basket.data.local.dao.GameDao
import luisitobez.jjvh.basket.data.local.dao.GameRosterDao

class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        competitionDao: CompetitionDao,
        gameDao: GameDao,
        gameRosterDao: GameRosterDao
    ) {

    }
}