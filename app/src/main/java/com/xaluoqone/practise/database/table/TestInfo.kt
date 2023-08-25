package com.xaluoqone.practise.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_info")
data class TestInfo(
    @PrimaryKey
    val id: Long,
    val data: String,
    val owner: String? = null
)