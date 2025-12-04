package com.example.instantweather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.instantweather.data.local.entity.DailyForecastEntity
import com.example.instantweather.data.local.entity.TimeStampEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM daily_forecast LIMIT 7")
    fun fetchWeatherForecast(): Flow<List<DailyForecastEntity>>

    @Transaction
    suspend fun updateWeatherForecast(dailyForecastList: List<DailyForecastEntity>) {
        val timeStamp = TimeStampEntity(lastUpdatedTime = System.currentTimeMillis())
        deleteAll()
        insertAll(dailyForecastList)
        updateTimeStamp(timeStamp)
    }

    @Query("DELETE FROM daily_forecast")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dailyForecastList: List<DailyForecastEntity>)

    @Query("SELECT last_updated_time FROM last_updated_timestamp WHERE id = 0 LIMIT 1")
    fun fetchLastUpdatedTime(): Flow<Long?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTimeStamp(timeStamp: TimeStampEntity)
}