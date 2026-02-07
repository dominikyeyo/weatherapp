package com.diego.weatherapp.feature.detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    q: String,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val decodedQuery = runCatching {
        URLDecoder.decode(q, StandardCharsets.UTF_8.toString())
    }.getOrElse { q }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(decodedQuery) {
        viewModel.loadForecast(decodedQuery)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        DetailContent(
            paddingValues = padding,
            query = decodedQuery,
            uiState = uiState
        )
    }
}

@Composable
private fun DetailContent(
    paddingValues: PaddingValues,
    query: String,
    uiState: DetailUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            DetailUiState.Idle -> {
                Text("Idle")
            }

            DetailUiState.Loading -> {
                CircularProgressIndicator()
                Text("Cargando pronóstico…")
            }

            is DetailUiState.Error -> {
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error
                )
                Text("Query: $query")
            }

            is DetailUiState.Success -> {
                Text(
                    text = "${uiState.forecast.locationName}, ${uiState.forecast.country}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Temp actual: ${uiState.forecast.current.tempC}°C"
                )
                Text(
                    text = "Estado: ${uiState.forecast.current.conditionText}"
                )
                Text("Días: ${uiState.forecast.days.size}")
            }
        }
    }
}
