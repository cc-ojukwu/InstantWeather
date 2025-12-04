package com.example.instantweather.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_forecast")
data class DailyForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "min_temperature")
    val minTemperature: String,

    @ColumnInfo(name = "max_temperature")
    val maxTemperature: String,

    @ColumnInfo(name = "weather_code")
    val weatherCode: Int
)