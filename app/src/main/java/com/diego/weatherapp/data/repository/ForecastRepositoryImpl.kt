package com.diego.weatherapp.data.repository

import com.diego.weatherapp.data.mapper.toDomain
import com.diego.weatherapp.data.remote.WeatherApiService
import com.diego.weatherapp.domain.model.Forecast
import com.diego.weatherapp.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val api: WeatherApiService
) : ForecastRepository {

    override suspend fun getForecast(query: String, days: Int): Forecast {
        return api.getForecast(query = query, days = days).toDomain()
    }
}
