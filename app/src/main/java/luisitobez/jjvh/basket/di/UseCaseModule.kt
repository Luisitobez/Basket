package luisitobez.jjvh.basket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luisitobez.jjvh.basket.domain.repository.GameRepository
import luisitobez.jjvh.basket.domain.repository.TeamRepository
import luisitobez.jjvh.basket.domain.usecase.GameUseCase
import luisitobez.jjvh.basket.domain.usecase.TeamUseCase

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGameUseCase(
        gameRepository: GameRepository
    ) = GameUseCase(gameRepository)

    @Provides
    fun provideTeamUseCase(
        teamRepository: TeamRepository
    ) = TeamUseCase(teamRepository)
}