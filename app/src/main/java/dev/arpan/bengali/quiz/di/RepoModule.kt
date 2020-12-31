package dev.arpan.bengali.quiz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.arpan.bengali.quiz.data.DefaultWordsRepository
import dev.arpan.bengali.quiz.data.WordsRepository
import dev.arpan.bengali.quiz.data.db.AppDatabase
import dev.arpan.bengali.quiz.data.db.WordsDao
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideWordsRepository(wordsDao: WordsDao): WordsRepository {
        return DefaultWordsRepository(wordsDao)
    }

    @Singleton
    @Provides
    fun provideWordsDao(appDatabase: AppDatabase): WordsDao {
        return appDatabase.wordsDao
    }
}