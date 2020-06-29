package com.uibk.databike.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter

@Database(entities = [DataPoint::class], version = 1, exportSchema = false)
abstract class DataBikeRoomDatabase : RoomDatabase() {
    abstract fun dataPointDao(): DataPointDao

    companion object {
        @Volatile
        private var INSTANCE: DataBikeRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DataBikeRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBikeRoomDatabase::class.java,
                    "data_bike_database"
                ).addCallback(
                    DataBikeDataBaseCallback(
                        scope
                    )
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

    private class DataBikeDataBaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let { database -> scope.launch { populateDatabase(database.dataPointDao()) } }
        }

        suspend fun populateDatabase(dataPointDao: DataPointDao) {
            val nowString = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            dataPointDao.deleteAll()

            dataPointDao.insert(DataPoint(0, 0,47.253f, 11.353f, 500, nowString, 1f, 1f, 1f))
            dataPointDao.insert(DataPoint(0, 0, 47.253f, 11.353f, 500, nowString,2f, 2f, 2f))
            dataPointDao.insert(DataPoint(0, 1, 47.253f, 11.353f, 500, nowString,3f, 3f, 3f))

        }
    }
}