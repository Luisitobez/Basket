package luisitobez.jjvh.basket.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luisitobez.jjvh.basket.data.repository.GameRepositoryImpl
import luisitobez.jjvh.basket.domain.repository.GameRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /*@Binds
    @Singleton
    abstract fun bindCompetitionRepository(
        competitionRepositoryImpl: CompetitionRepositoryImpl
    ): CompetitionRepository*/

    @Binds
    @Singleton
    abstract fun bindGameRepository(
        gameRepositoryImpl: GameRepositoryImpl
    ): GameRepository
}