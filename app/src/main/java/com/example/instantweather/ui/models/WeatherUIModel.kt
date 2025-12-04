package com.example.instantweather.ui.models

import com.example.instantweather.R

data class WeatherUIModel(
    val date: String,

    val dayOfTheWeek: String,

    val minTemperature: String,

    val maxTemperature: String,

    val weatherCondition: WeatherCondition
)

enum class WeatherCondition(val code: Int, val description: String, val icon: Int) {
    WMO_0(0, "Clear sky", R.drawable.clear_sky),
    WMO_1(1, "Mainly clear", R.drawable.mainly_clear),
    WMO_2(2, "Partly cloudy", R.drawable.partly_cloudy),
    WMO_3(3, "Overcast", R.drawable.overcast),
    WMO_45(45, "Fog", R.drawable.fog),
    WMO_48(48, "Depositing rime fog", R.drawable.fog),
    WMO_51(51, "Drizzle: Light", R.drawable.drizzle),
    WMO_53(53, "Drizzle: Moderate", R.drawable.drizzle),
    WMO_55(55, "Drizzle: Dense", R.drawable.drizzle),
    WMO_56(56, "Freezing Drizzle: Light", R.drawable.freezing_rain),
    WMO_57(57, "Freezing Drizzle: Dense", R.drawable.freezing_rain),
    WMO_61(61, "Rain: Slight", R.drawable.drizzle),
    WMO_63(63, "Rain: Moderate", R.drawable.drizzle),
    WMO_65(65, "Rain: Heavy", R.drawable.rain),
    WMO_66(66, "Freezing Rain: Light", R.drawable.freezing_rain),
    WMO_67(67, "Freezing Rain: Heavy", R.drawable.freezing_rain),
    WMO_71(71, "Snowfall: Slight", R.drawable.snowing),
    WMO_73(73, "Snowfall: Moderate", R.drawable.snowing),
    WMO_75(75, "Snowfall: Heavy", R.drawable.snowing),
    WMO_77(77, "Snow grains", R.drawable.snowing),
    WMO_80(80, "Rain showers: Slight", R.drawable.rain),
    WMO_81(81, "Rain showers: Moderate", R.drawable.rain),
    WMO_82(82, "Rain showers: Violent", R.drawable.rain),
    WMO_85(85, "Snow showers: Slight", R.drawable.snowing),
    WMO_86(86, "Snow showers: Heavy", R.drawable.snowing),
    WMO_95(95, "Thunderstorm: Slight/Moderate", R.drawable.thunderstorm_slight),
    WMO_96(96, "Thunderstorm with slight hail", R.drawable.thunderstorm),
    WMO_99(99, "Thunderstorm with heavy hail", R.drawable.thunderstorm)
}

