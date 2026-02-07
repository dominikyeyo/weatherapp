package com.diego.weatherapp.feature.search.presentation

import com.diego.weatherapp.domain.model.Location

sealed interface SearchUiState {

    object Idle : SearchUiState

    object Loading : SearchUiState

    data class Success(val results: List<Location>) : SearchUiState


    data class Error(
        val message: String
    ) : SearchUiState
}
