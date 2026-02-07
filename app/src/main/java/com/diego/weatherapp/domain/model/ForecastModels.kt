package com.diego.weatherapp.domain.model

data class Forecast(
    val locationName: String,
    val country: String,
    val current: CurrentWeather,
    val days: List<ForecastDay>
)

data class CurrentWeather(
    val tempC: Double,
    val conditionText: String,
    val conditionIconUrl: String
)

data class ForecastDay(
    val date: String,
    val avgTempC: Double,
    val conditionText: String,
    val conditionIconUrl: String
)
