package com.lucas.frame2.data.db.dao

import androidx.room.*
import com.lucas.frame2.data.db.bean.TestBean

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(testBean: TestBean)

    @Query("select * from user limit 1")
    fun getUser(): TestBean?

}