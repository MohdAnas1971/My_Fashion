package com.example.myfashion.data.remot

import com.example.myfashion.domain.model.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApi(
    private val client: HttpClient
) {

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): WeatherResponse =
        client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("appid", apiKey)
            parameter("units", "metric")
        }.body()
}
