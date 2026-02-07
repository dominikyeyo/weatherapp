package com.diego.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class ConditionDto(
    @Json(name = "text") val text: String,
    @Json(name = "icon") val icon: String
)
