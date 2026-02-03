package com.example.myfashion.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

@Serializable
data class Main(
    val temp: Double,
    val humidity: Int
)

@Serializable
data class Weather(
    val main: String,
    val description: String,
    val icon: String
)
