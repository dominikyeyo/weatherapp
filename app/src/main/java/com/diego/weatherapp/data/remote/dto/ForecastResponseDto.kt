package com.diego.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class ForecastResponseDto(
    @Json(name = "location") val location: LocationInfoDto,
    @Json(name = "current") val current: CurrentDto,
    @Json(name = "forecast") val forecast: ForecastDto
)
