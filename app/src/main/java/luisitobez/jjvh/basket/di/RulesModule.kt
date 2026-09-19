package luisitobez.jjvh.basket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import luisitobez.jjvh.basket.domain.rules.FoulRules
import luisitobez.jjvh.basket.domain.rules.ScoreRules


@Module
@InstallIn(SingletonComponent::class)
object RulesModule {
    @Provides
    @Singleton
    fun provideFoulRules(): FoulRules = FoulRules()

    @Provides @Singleton
    fun provideScoreRules(foulRules: FoulRules): ScoreRules = ScoreRules(foulRules)
}