package com.example.instantweather.di

import com.example.instantweather.data.WeatherRepository
import com.example.instantweather.data.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl) : WeatherRepository
}