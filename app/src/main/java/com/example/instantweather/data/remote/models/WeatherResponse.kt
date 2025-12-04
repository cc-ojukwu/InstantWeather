package com.example.instantweather.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDto(
    @Json(name="daily_units")
    val units: ForecastUnitsDto,

    @Json(name="daily")
    val forecast: ForecastDto
)

@JsonClass(generateAdapter = true)
data class ForecastUnitsDto(
    @Json(name="time")
    val dateFormat: String,

    @Json(name="weather_code")
    val weatherCode: String,

    @Json(name="temperature_2m_max")
    val maxTempUnit: String,

    @Json(name="temperature_2m_min")
    val minTempUnit: String
)

@JsonClass(generateAdapter = true)
data class ForecastDto(
    @Json(name="time")
    val forecastDates: List<String>,

    @Json(name="weather_code")
    val weatherCode: List<Int>,

    @Json(name="temperature_2m_max")
    val tempMax: List<Double>,

    @Json(name="temperature_2m_min")
    val tempMin: List<Double>
)
