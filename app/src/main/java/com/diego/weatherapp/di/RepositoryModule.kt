package com.diego.weatherapp.di

import com.diego.weatherapp.data.repository.ForecastRepositoryImpl
import com.diego.weatherapp.data.repository.WeatherRepositoryImpl
import com.diego.weatherapp.domain.repository.ForecastRepository
import com.diego.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindForecastRepository(
        impl: ForecastRepositoryImpl
    ): ForecastRepository
}
