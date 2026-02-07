package com.diego.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class CurrentDto(
    @Json(name = "temp_c") val tempC: Double,
    @Json(name = "condition") val condition: ConditionDto
)
