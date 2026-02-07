package com.diego.weatherapp.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.weatherapp.data.remote.WeatherApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestSearchViewModel @Inject constructor(
    private val api: WeatherApiService
) : ViewModel() {

    private val _state = MutableStateFlow("Idle")
    val state = _state.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            _state.value = "Loading..."
            runCatching {
                api.searchLocations(query)
            }.onSuccess { list ->
                _state.value = "OK: ${list.take(5).joinToString { "${it.name} (${it.country})" }}"
            }.onFailure { e ->
                _state.value = "ERROR: ${e.message}"
            }
        }
    }
}
