package com.xaluoqone.practise.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xaluoqone.practise.App

@Database(
    entities = [

    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val instance by lazy {
            val build =
                Room.databaseBuilder(App.instance, AppDatabase::class.java, "practise")
            build.build()
        }
    }
}
