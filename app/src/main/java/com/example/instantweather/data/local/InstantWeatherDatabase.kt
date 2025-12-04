package com.example.instantweather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.instantweather.data.local.entity.DailyForecastEntity
import com.example.instantweather.data.local.entity.TimeStampEntity

@Database(entities = [DailyForecastEntity::class, TimeStampEntity::class], version = 1)
abstract class InstantWeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}