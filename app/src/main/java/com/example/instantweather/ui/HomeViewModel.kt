package com.example.instantweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instantweather.data.WeatherRepository
import com.example.instantweather.data.local.entity.DailyForecastEntity
import com.example.instantweather.data.remote.models.Result
import com.example.instantweather.ui.models.WeatherCondition
import com.example.instantweather.ui.models.WeatherUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchWeatherForecast()
        refreshWeatherForecast()
    }

    fun fetchWeatherForecast() {
        _uiState.value = _uiState.value.copy(isBusy = true)

        viewModelScope.launch {
            weatherRepository.fetchWeatherForecast().collect { result ->
                _uiState.value = _uiState.value.copy(
                    isBusy = false,
                    forecastList = result.toUIModels()
                )
                fetchLastUpdatedTime()
            }
        }
    }

    fun refreshWeatherForecast() {
        viewModelScope.launch {
            weatherRepository.refreshForecast().collect { result ->
                if (result is Result.Error && _uiState.value.forecastList.isEmpty()) {
                    _uiState.value = _uiState.value.copy(isBusy = false, isError = true)
                }
                fetchLastUpdatedTime()
            }
        }
    }

    fun fetchLastUpdatedTime() {
        viewModelScope.launch {
            weatherRepository.fetchLastUpdatedTime().collect { time ->
                time?.let {
                    _uiState.value = _uiState.value.copy(lastUpdatedTime = time)
                }
            }
        }
    }
}

data class HomeUIState(
    val isBusy: Boolean = false,
    val isError: Boolean = false,
    val lastUpdatedTime: Long? = null,
    val forecastList: List<WeatherUIModel> = listOf()
)

private fun List<DailyForecastEntity>.toUIModels(): List<WeatherUIModel> {
    return this.mapNotNull { dbModel ->
        val dayOfWeek = getDayOfWeek(dbModel.date)
        val weatherCondition = WeatherCondition.entries.firstOrNull { it.code == dbModel.weatherCode }
        if (dayOfWeek != null && weatherCondition != null) {
            WeatherUIModel(
                date = dbModel.date,
                dayOfTheWeek = dayOfWeek,
                minTemperature = dbModel.minTemperature,
                maxTemperature = dbModel.maxTemperature,
                weatherCondition = weatherCondition
            )
        } else null
    }
}