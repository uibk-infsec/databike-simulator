package com.uibk.databike.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [DataPoint::class], version = 1, exportSchema = false)
abstract class DataPointRoomDatabase : RoomDatabase() {
    abstract fun dataPointDao(): DataPointDao

    companion object {
        @Volatile
        private var INSTANCE: DataPointRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DataPointRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataPointRoomDatabase::class.java,
                    "data_point_database"
                ).addCallback(
                    DataPointDatabaseCallback(
                        scope
                    )
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

    private class DataPointDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let { database -> scope.launch { populateDatabase(database.dataPointDao()) } }
        }

        suspend fun populateDatabase(dataPointDao: DataPointDao) {
            dataPointDao.deleteAll()

            dataPointDao.insert(DataPoint(0, 1f, 1f, 1f))
            dataPointDao.insert(DataPoint(0, 2f, 2f, 2f))
            dataPointDao.insert(DataPoint(0, 3f, 3f, 3f))

        }
    }
}