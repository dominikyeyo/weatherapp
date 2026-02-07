package com.diego.weatherapp.data.repository

import com.diego.weatherapp.data.mapper.toDomain
import com.diego.weatherapp.data.remote.WeatherApiService
import com.diego.weatherapp.domain.model.Location
import com.diego.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService
) : WeatherRepository {

    override suspend fun searchLocations(query: String): List<Location> {
        return api.searchLocations(query)
            .mapNotNull { it.toDomain() }
    }
}
