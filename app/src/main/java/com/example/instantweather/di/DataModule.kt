package com.example.instantweather.di

import android.content.Context
import androidx.room.Room
import com.example.instantweather.data.local.InstantWeatherDatabase
import com.example.instantweather.data.local.LocalDataSource
import com.example.instantweather.data.local.LocalDataSourceImpl
import com.example.instantweather.data.local.WeatherDao
import com.example.instantweather.data.remote.RemoteDataSource
import com.example.instantweather.data.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): InstantWeatherDatabase {
        return Room.databaseBuilder(
            context,
            InstantWeatherDatabase::class.java, "InstantWeatherDB.db"
        )
            .fallbackToDestructiveMigration() //should be removed for a production app
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(database: InstantWeatherDatabase): WeatherDao = database.weatherDao()

    @Singleton
    @Provides
    fun provideRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource {
        return remoteDataSourceImpl
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource {
        return localDataSourceImpl
    }
}