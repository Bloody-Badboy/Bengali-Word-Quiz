package dev.arpan.bengali.quiz.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.arpan.bengali.quiz.data.model.Word

@Database(
    entities = [Word::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val wordsDao: WordsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        private const val DATABASE_NAME = "words.db"
        private const val ASSETS_DATABASE = "database/words.db"

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE
                ?: synchronized(LOCK) {
                    INSTANCE
                        ?: buildDatabase(context)
                            .also { INSTANCE = it }
                }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .createFromAsset(ASSETS_DATABASE)
                .fallbackToDestructiveMigration()
                .build()
    }
}