package com.diego.weatherapp.feature.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.weatherapp.domain.usecase.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Idle)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadForecast(query: String) {

        if (_uiState.value is DetailUiState.Loading) return

        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            runCatching {
                getForecastUseCase(query, days = 3)
            }.onSuccess { forecast ->
                _uiState.value = DetailUiState.Success(forecast)
            }.onFailure { throwable ->
                val message = when {
                    throwable.message?.contains("Unable to resolve host", ignoreCase = true) == true ->
                        "Sin conexión. Revisa tu internet e intenta de nuevo."
                    else -> throwable.message ?: "Ocurrió un error inesperado"
                }
                _uiState.value = DetailUiState.Error(message)
            }
        }
    }
}
