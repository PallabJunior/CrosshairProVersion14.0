package com.customscopecommunity.crosshairpro.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Position::class],
    version = 1
)
abstract class PositionDatabase : RoomDatabase() {

    abstract fun getPositionDao(): PositionDao

    companion object {
        @Volatile
        private var instance: PositionDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PositionDatabase::class.java,
            "position_database"
        ).build()
    }
}