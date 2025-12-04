package com.example.instantweather.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_updated_timestamp")
data class TimeStampEntity(
    @PrimaryKey
    val id: Int = 0,

    @ColumnInfo(name = "last_updated_time")
    val lastUpdatedTime: Long?
)
