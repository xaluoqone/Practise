package com.xaluoqone.practise.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xaluoqone.practise.App
import com.xaluoqone.practise.database.dao.TestInfoDao
import com.xaluoqone.practise.database.table.TestInfo

@Database(
    entities = [
        TestInfo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun testInfoDao(): TestInfoDao

    companion object {
        val instance by lazy {
            val build =
                Room.databaseBuilder(App.instance, AppDatabase::class.java, "practise")
            build.build()
        }
    }
}
