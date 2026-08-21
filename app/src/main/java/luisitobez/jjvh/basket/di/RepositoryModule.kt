package luisitobez.jjvh.basket.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luisitobez.jjvh.basket.data.repository.CompetitionRepositoryImpl
import luisitobez.jjvh.basket.data.repository.GameEventRepositoryImpl
import luisitobez.jjvh.basket.data.repository.GameRepositoryImpl
import luisitobez.jjvh.basket.data.repository.GameRosterRepositoryImpl
import luisitobez.jjvh.basket.data.repository.GameStaffRepositoryImpl
import luisitobez.jjvh.basket.data.repository.TeamRepositoryImpl
import luisitobez.jjvh.basket.data.repository.TeamPeriodFoulRepositoryImpl
import luisitobez.jjvh.basket.domain.repository.CompetitionRepository
import luisitobez.jjvh.basket.domain.repository.GameStaffRepository
import luisitobez.jjvh.basket.domain.repository.TeamPeriodFoulRepository
import luisitobez.jjvh.basket.domain.repository.GameEventRepository
import luisitobez.jjvh.basket.domain.repository.GameRepository
import luisitobez.jjvh.basket.domain.repository.GameRosterRepository
import luisitobez.jjvh.basket.domain.repository.TeamRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindCompetitionRepository(implementation: CompetitionRepositoryImpl): CompetitionRepository

    @Binds @Singleton
    abstract fun bindGameEventRepository(implementation: GameEventRepositoryImpl): GameEventRepository

    @Binds @Singleton
    abstract fun bindGameRepository(implementation: GameRepositoryImpl): GameRepository

    @Binds @Singleton
    abstract fun bindGameRosterRepository(implementation: GameRosterRepositoryImpl): GameRosterRepository

    @Binds @Singleton
    abstract fun bindGameStaffRepository(implementation: GameStaffRepositoryImpl): GameStaffRepository

    @Binds @Singleton
    abstract fun bindTeamRepository(implementation: TeamRepositoryImpl): TeamRepository

    @Binds @Singleton
    abstract fun bindTeamPeriodFoulRepository(implementation: TeamPeriodFoulRepositoryImpl): TeamPeriodFoulRepository

}
