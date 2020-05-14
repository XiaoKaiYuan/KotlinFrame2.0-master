package com.lucas.frame2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucas.frame2.data.db.dao.UserDao
import com.lucas.frame2.data.db.bean.TestBean

@Database(entities = [TestBean::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}