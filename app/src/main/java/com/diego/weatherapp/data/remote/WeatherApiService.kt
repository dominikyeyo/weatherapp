package com.diego.weatherapp.data.remote

import com.diego.weatherapp.data.remote.dto.ForecastResponseDto
import com.diego.weatherapp.data.remote.dto.LocationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("search.json")
    suspend fun searchLocations(
        @Query("q") query: String
    ): List<LocationDto>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") query: String,
        @Query("days") days: Int = 3
    ): ForecastResponseDto
}