package com.example.myfashion.data.repositoryImp

import com.example.myfashion.data.remot.WeatherApi
import com.example.myfashion.domain.model.WeatherResponse
import com.example.myfashion.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {
    private val apiKey = "de79cb7960592fdb841bd41d493d4678"

    override suspend fun getWeather(
        lat: Double,
        lon: Double
    ): Result<WeatherResponse> =
        runCatching {
            api.getCurrentWeather(lat, lon, apiKey)
        }
}
