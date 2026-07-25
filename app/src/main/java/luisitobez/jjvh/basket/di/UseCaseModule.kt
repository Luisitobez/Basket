package luisitobez.jjvh.basket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luisitobez.jjvh.basket.domain.repository.GameRepository
import luisitobez.jjvh.basket.domain.usecase.GameUseCase

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGameUseCase(
        gameRepository: GameRepository
    ) = GameUseCase(gameRepository)

}