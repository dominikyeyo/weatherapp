package com.diego.weatherapp.domain.repository

import com.diego.weatherapp.domain.model.Forecast

interface ForecastRepository {
    suspend fun getForecast(query: String, days: Int = 3): Forecast
}
