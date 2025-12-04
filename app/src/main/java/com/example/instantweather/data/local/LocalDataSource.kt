package com.example.instantweather.data.local

import com.example.instantweather.data.local.entity.DailyForecastEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {
    fun fetchWeatherForecast(): Flow<List<DailyForecastEntity>>

    suspend fun fetchLastUpdatedTime():  Flow<Long?>

    suspend fun updateWeatherForecast(dailyForecastList: List<DailyForecastEntity>)
}

class LocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : LocalDataSource {
    override fun fetchWeatherForecast(): Flow<List<DailyForecastEntity>> =
        weatherDao.fetchWeatherForecast()

    override suspend fun fetchLastUpdatedTime(): Flow<Long?> = weatherDao.fetchLastUpdatedTime()

    override suspend fun updateWeatherForecast(dailyForecastList: List<DailyForecastEntity>) =
        weatherDao.updateWeatherForecast(dailyForecastList)
}

