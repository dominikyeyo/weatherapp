package com.diego.weatherapp.domain.usecase

import com.diego.weatherapp.domain.model.Location
import com.diego.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchLocationsUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(query: String): List<Location> {
        return repository.searchLocations(query)
    }
}
