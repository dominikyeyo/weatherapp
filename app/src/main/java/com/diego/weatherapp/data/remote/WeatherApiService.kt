package com.diego.weatherapp.data.remote

import com.diego.weatherapp.data.remote.dto.LocationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("search.json")
    suspend fun searchLocations(
        @Query("q") query: String
    ): List<LocationDto>
}
