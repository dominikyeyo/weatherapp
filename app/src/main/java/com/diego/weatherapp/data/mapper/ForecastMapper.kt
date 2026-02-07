package com.diego.weatherapp.data.mapper

import com.diego.weatherapp.data.remote.dto.ForecastResponseDto
import com.diego.weatherapp.domain.model.CurrentWeather
import com.diego.weatherapp.domain.model.Forecast
import com.diego.weatherapp.domain.model.ForecastDay

fun ForecastResponseDto.toDomain(): Forecast {
    return Forecast(
        locationName = location.name,
        country = location.country,
        current = CurrentWeather(
            tempC = current.tempC,
            conditionText = current.condition.text,
            conditionIconUrl = current.condition.icon.toFullIconUrl()
        ),
        days = forecast.forecastDays.map { forecastDay ->
            ForecastDay(
                date = forecastDay.date,
                avgTempC = forecastDay.day.avgTempC,
                conditionText = forecastDay.day.condition.text,
                conditionIconUrl = forecastDay.day.condition.icon.toFullIconUrl()
            )
        }
    )
}

private fun String.toFullIconUrl(): String {
    return if (startsWith("//")) "https:$this" else this
}
