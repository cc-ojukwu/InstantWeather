package com.example.instantweather.data.remote

import com.example.instantweather.data.remote.models.WeatherDto
import com.example.instantweather.di.IoDispatcher
import com.example.instantweather.data.remote.models.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getWeatherForecast():   Result<WeatherDto>
}

class RemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun getWeatherForecast(): Result<WeatherDto> =  withContext(ioDispatcher) {
        return@withContext try {
            val result = apiService.getWeatherForecast()
            val body = result.body()
            if (result.isSuccessful && body != null) {
                Result.Success(body)
            } else {
                Result.Error(null)
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}