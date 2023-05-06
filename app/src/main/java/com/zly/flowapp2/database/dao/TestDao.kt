package com.zly.flowapp2.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zly.flowapp2.database.bean.DaoTestBean
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    @Transaction
    @Query("select * from member limit 1")
    fun getTestFlow(): Flow<DaoTestBean?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(member: DaoTestBean)
}