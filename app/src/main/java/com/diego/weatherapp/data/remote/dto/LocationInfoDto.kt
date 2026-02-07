package com.diego.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class LocationInfoDto(
    @Json(name = "name") val name: String,
    @Json(name = "country") val country: String
)
