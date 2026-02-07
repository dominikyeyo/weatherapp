package com.diego.weatherapp.domain.usecase

import com.diego.weatherapp.domain.model.Forecast
import com.diego.weatherapp.domain.repository.ForecastRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {
    suspend operator fun invoke(query: String, days: Int = 3): Forecast {
        return repository.getForecast(query = query, days = days)
    }
}
