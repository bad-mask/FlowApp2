package com.zly.flowapp2.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zly.flowapp2.app.App
import com.zly.flowapp2.database.bean.DaoTestBean
import com.zly.flowapp2.database.dao.TestDao

@Database(entities = [DaoTestBean::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        val DB_NAME = "test.db"

        fun getInstance(): AppDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) INSTANCE = Room.databaseBuilder(
                        App.context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}