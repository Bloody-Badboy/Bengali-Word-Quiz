package dev.arpan.bengali.quiz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.arpan.bengali.quiz.data.DefaultWordsRepository
import dev.arpan.bengali.quiz.data.WordsRepository
import dev.arpan.bengali.quiz.data.db.AppDatabase
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideWordsRepository(appDatabase: AppDatabase): WordsRepository {
        return DefaultWordsRepository(appDatabase)
    }
}