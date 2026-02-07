package com.diego.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class ForecastDto(
    @Json(name = "forecastday") val forecastDays: List<ForecastDayDto>
)
