package com.example.compass

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataPoint::class], version = 1, exportSchema = false)
abstract class DataPointRoomDatabase : RoomDatabase() {
    abstract fun dataPointDao(): DataPointDao

    companion object {
        @Volatile
        private var INSTANCE: DataPointRoomDatabase? = null

        fun getDatabase(context: Context): DataPointRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataPointRoomDatabase::class.java,
                    "data_point_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}