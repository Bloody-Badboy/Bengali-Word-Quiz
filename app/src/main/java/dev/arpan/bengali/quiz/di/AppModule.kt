package dev.arpan.bengali.quiz.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.arpan.bengali.quiz.data.db.AppDatabase
import dev.arpan.bengali.quiz.prefs.PreferenceStorage
import dev.arpan.bengali.quiz.prefs.SharedPreferenceStorage
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providePreferenceStorage(@ApplicationContext context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}