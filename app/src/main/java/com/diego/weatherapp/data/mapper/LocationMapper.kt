package com.diego.weatherapp.data.mapper

import com.diego.weatherapp.data.remote.dto.LocationDto
import com.diego.weatherapp.domain.model.Location

fun LocationDto.toDomain(): Location? {
    val safeId = id ?: return null
    val safeName = name ?: return null
    val safeCountry = country ?: return null
    val safeLat = lat ?: return null
    val safeLon = lon ?: return null

    return Location(
        id = safeId,
        name = safeName,
        country = safeCountry,
        lat = safeLat,
        lon = safeLon
    )
}
