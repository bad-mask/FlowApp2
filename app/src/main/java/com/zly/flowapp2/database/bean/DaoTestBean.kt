package com.zly.flowapp2.database.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "member")
data class DaoTestBean (
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = "lase_use_time")
    @SerializedName("lase_use_time")
    val lastUseTime: Long = 0L
)