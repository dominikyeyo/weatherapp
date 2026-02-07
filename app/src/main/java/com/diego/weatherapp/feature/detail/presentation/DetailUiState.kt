package com.diego.weatherapp.feature.detail.presentation

import com.diego.weatherapp.domain.model.Forecast

sealed interface DetailUiState {
    data object Idle : DetailUiState
    data object Loading : DetailUiState
    data class Success(val forecast: Forecast) : DetailUiState
    data class Error(val message: String) : DetailUiState
}
