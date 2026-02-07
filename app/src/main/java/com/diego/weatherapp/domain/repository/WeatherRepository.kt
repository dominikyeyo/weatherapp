package com.diego.weatherapp.domain.repository

import com.diego.weatherapp.domain.model.Location


interface WeatherRepository {
    suspend fun searchLocations(query: String): List<Location>
}