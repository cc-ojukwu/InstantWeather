package com.example.instantweather.data.remote

import com.example.instantweather.data.remote.models.WeatherDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v1/forecast?latitude=53.48&longitude=-2.24&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=auto")
    suspend fun getWeatherForecast(): Response<WeatherDto>
}