package com.diego.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class ForecastDayDto(
    @Json(name = "date") val date: String,
    @Json(name = "day") val day: DayDto
)
