package com.example.instantweather.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.instantweather.ui.models.WeatherUIModel
import com.example.instantweather.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.instantweather.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState: HomeUIState by viewModel.uiState.collectAsStateWithLifecycle()
    val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = uiState.isBusy,
        onRefresh = viewModel::refreshWeatherForecast
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.forecastList.firstOrNull { it.date == today }?.let { uIModel ->
                item {
                    TodayForecastView(uIModel)

                    Spacer(modifier = Modifier.height(60.dp))
                }
            }

            uiState.lastUpdatedTime?.let { lastUpdatedTime ->
                if (lastUpdatedTime.isOlderThanOneMinute()) {
                    item {
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            text = "Last updated at: ${formatTo12Hour(lastUpdatedTime)}",
                            style = Typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }

            items(uiState.forecastList) { dayForecastModel ->
                ForecastView(weatherModel = dayForecastModel)

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        if (uiState.forecastList.isEmpty() && uiState.isError) {
            RetryDialog(onRetry = viewModel::refreshWeatherForecast)
        }
    }
}

@Composable
private fun TodayForecastView(weatherModel: WeatherUIModel) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 48.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.tertiary,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(R.string.city),
                style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = weatherModel.maxTemperature,
                style = Typography.displayMedium
            )

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "${weatherModel.maxTemperature}/${weatherModel.minTemperature}",
                style = Typography.bodyLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .size(28.dp),
                    painter = painterResource(weatherModel.weatherCondition.icon),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = weatherModel.weatherCondition.description,
                    style = Typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun RetryDialog(onRetry: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(top = 48.dp)
            .padding(horizontal = 48.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(R.string.error_message),
                style = Typography.bodyLarge
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                onClick = onRetry
            ) {
                Text(
                    text = stringResource(R.string.retry)
                )
            }
        }
    }
}

@Composable
private fun ForecastView(weatherModel: WeatherUIModel) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primary,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp),
                painter = painterResource(weatherModel.weatherCondition.icon),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = weatherModel.dayOfTheWeek,
                    style = Typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = weatherModel.weatherCondition.description,
                    style = Typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "${weatherModel.maxTemperature} / ${weatherModel.minTemperature}",
                style = Typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

private fun Long.isOlderThanOneMinute(): Boolean {
    val now = System.currentTimeMillis()
    //we consider the weather data stale
    return (now - this) > 60_000L
}