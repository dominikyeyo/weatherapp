package com.diego.weatherapp.feature.search.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchScreen(
    onNavigateToDetail: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = uiState is SearchUiState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.onQueryChanged(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Ciudad") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
        }

        when (uiState) {

            is SearchUiState.Idle -> Unit

            is SearchUiState.Loading -> Unit

            is SearchUiState.Error -> {
                Text(
                    text = (uiState as SearchUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is SearchUiState.Success -> {
                val results = (uiState as SearchUiState.Success).results

                if (results.isEmpty()) {
                    Text(
                        text = "No se encontraron resultados",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyColumn {
                        items(results) { location ->
                            LocationItem(
                                location = location,
                                onClick = {
                                    onNavigateToDetail("${location.lat},${location.lon}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
