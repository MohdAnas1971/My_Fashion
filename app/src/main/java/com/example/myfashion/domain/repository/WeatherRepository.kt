package com.example.myfashion.domain.repository

import com.example.myfashion.domain.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Result<WeatherResponse>
}
