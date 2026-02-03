package com.example.myfashion.presentasion.home

import com.example.myfashion.domain.model.WeatherResponse

sealed interface WeatherUiState {
    object Loading : WeatherUiState
    data class Success(val data: WeatherResponse) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}
