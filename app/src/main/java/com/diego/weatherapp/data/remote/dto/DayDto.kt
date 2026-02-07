package com.diego.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class DayDto(
    @Json(name = "avgtemp_c") val avgTempC: Double,
    @Json(name = "condition") val condition: ConditionDto
)
