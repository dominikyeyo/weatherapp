package com.diego.weatherapp.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.weatherapp.domain.usecase.SearchLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchLocationsUseCase: SearchLocationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {
        queryFlow
            .debounce(500)
            .distinctUntilChanged()
            .onEach { text ->
                if (text.isBlank()) {
                    _uiState.value = SearchUiState.Idle
                }
            }
            .filter { it.isNotBlank() }
            .flatMapLatest { text ->
                flow {
                    _uiState.value = SearchUiState.Loading
                    val results = searchLocationsUseCase(text)
                    emit(results)
                }
            }
            .onEach { results ->
                _uiState.value = SearchUiState.Success(results)
            }
            .catch { e ->
                val message = when (e) {
                    is HttpException -> "HTTP ${e.code()}"
                    is IOException -> "Error de red"
                    else -> "Error inesperado"
                }
                _uiState.value = SearchUiState.Error(message)
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChanged(text: String) {
        queryFlow.value = text
    }
}
