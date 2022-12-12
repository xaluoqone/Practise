package com.xaluoqone.practise.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xaluoqone.practise.database.table.TestInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface TestInfoDao {

    @Query("select * from test_info")
    fun queryAllTestInfo(): Flow<List<TestInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestInfoList(infoList: List<TestInfo>)

    @Query("delete from test_info where id not in(:ids)")
    suspend fun deleteTestInfo(ids: List<Long>): Int
}