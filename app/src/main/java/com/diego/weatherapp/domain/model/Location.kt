package com.diego.weatherapp.domain.model

data class Location(
    val id: Int,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double
)
