package com.diego.weatherapp.feature.detail.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diego.weatherapp.domain.model.Forecast
import com.diego.weatherapp.domain.model.ForecastDay
import kotlin.math.roundToInt

@Composable
fun DetailContent(
    paddingValues: PaddingValues,
    query: String,
    uiState: DetailUiState,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (uiState) {
            DetailUiState.Idle -> Unit

            DetailUiState.Loading -> LoadingState()

            is DetailUiState.Error -> ErrorState(
                message = uiState.message,
                query = query,
                onRetry = onRetry
            )

            is DetailUiState.Success -> SuccessState(forecast = uiState.forecast)
        }
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("Cargando pronóstico…")
    }
}

@Composable
private fun ErrorState(
    message: String,
    query: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Text(text = "Query: $query", style = MaterialTheme.typography.bodySmall)
        Button(onClick = onRetry) { Text("Reintentar") }
    }
}

@Composable
private fun SuccessState(forecast: Forecast) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CurrentHeader(forecast)
            ForecastDaysColumn(forecast.days)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { CurrentHeader(forecast) }
            item { Text("Pronóstico (3 días)", style = MaterialTheme.typography.titleSmall) }
            items(forecast.days) { day ->
                ForecastDayCard(day)
            }
            item { Spacer(modifier = Modifier.height(4.dp)) }
        }
    }
}

@Composable
private fun CurrentHeader(forecast: Forecast) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "${forecast.locationName}, ${forecast.country}",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AsyncImage(
                    model = forecast.current.conditionIconUrl,
                    contentDescription = forecast.current.conditionText,
                    modifier = Modifier
                        .height(56.dp)
                        .padding(2.dp)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = forecast.current.conditionText,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Temp actual: ${forecast.current.tempC.roundToInt()}°C",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun ForecastDaysColumn(days: List<ForecastDay>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Pronóstico (3 días)", style = MaterialTheme.typography.titleSmall)
        days.forEach { day -> ForecastDayCard(day) }
    }
}

@Composable
private fun ForecastDayCard(day: ForecastDay) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = day.conditionIconUrl,
                contentDescription = day.conditionText,
                modifier = Modifier.height(44.dp).padding(vertical = 2.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = day.date, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = day.conditionText,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = "${day.avgTempC.roundToInt()}°C",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
