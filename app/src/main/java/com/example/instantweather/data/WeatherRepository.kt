package com.example.instantweather.data

import com.example.instantweather.data.local.LocalDataSource
import com.example.instantweather.data.local.entity.DailyForecastEntity
import com.example.instantweather.data.remote.RemoteDataSource
import com.example.instantweather.data.remote.models.ForecastDto
import com.example.instantweather.data.remote.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface WeatherRepository {
    fun fetchWeatherForecast(): Flow<List<DailyForecastEntity>>

    suspend fun refreshForecast(): Flow<Result<Boolean>>

    suspend fun fetchLastUpdatedTime(): Flow<Long?>
}

class WeatherRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): WeatherRepository {
    override fun fetchWeatherForecast(): Flow<List<DailyForecastEntity>> =
        localDataSource.fetchWeatherForecast()

    override suspend fun refreshForecast(): Flow<Result<Boolean>> = flow {
        when(val remoteData = remoteDataSource.getWeatherForecast()) {
            is Result.Success -> {
                val dailyForecastEntityList = remoteData.data.forecast.toEntityModels(
                    minTempUnit = remoteData.data.units.minTempUnit,
                    maxTempUnit = remoteData.data.units.maxTempUnit
                )
                localDataSource.updateWeatherForecast(dailyForecastEntityList)
                emit(Result.Success(true))
            }
            is Result.Error -> {
                emit(Result.Error())
            }
            else -> {
                emit(Result.Loading)
            }
        }
    }

    override suspend fun fetchLastUpdatedTime(): Flow<Long?> = localDataSource.fetchLastUpdatedTime()
}

fun ForecastDto.toEntityModels(
    minTempUnit: String,
    maxTempUnit: String
): List<DailyForecastEntity> {
    val expectedSize = forecastDates.size
    if (weatherCode.size != expectedSize ||
        tempMin.size != expectedSize ||
        tempMax.size != expectedSize
    ) {
        // Lists are inconsistent, return empty
        return emptyList()
    }

    return (0 until expectedSize).map { index ->
        DailyForecastEntity(
            date = forecastDates[index],
            minTemperature = "${tempMin[index]}$minTempUnit",
            maxTemperature = "${tempMax[index]}$maxTempUnit",
            weatherCode = weatherCode[index]
        )
    }
}

