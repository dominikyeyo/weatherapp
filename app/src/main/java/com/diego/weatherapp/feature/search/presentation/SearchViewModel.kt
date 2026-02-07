package com.diego.weatherapp.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.weatherapp.domain.usecase.SearchLocationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchLocationsUseCase: SearchLocationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun search(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Error("Ingresa una ciudad")
            return
        }

        _uiState.value = SearchUiState.Loading

        viewModelScope.launch {
            try {
                val results = searchLocationsUseCase(query)
                _uiState.value = SearchUiState.Success(results)
            } catch (e: HttpException) {
                _uiState.value = SearchUiState.Error("HTTP ${e.code()}")
            } catch (e: IOException) {
                _uiState.value = SearchUiState.Error("Error de red")
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error("Error inesperado")
            }
        }
    }
}
