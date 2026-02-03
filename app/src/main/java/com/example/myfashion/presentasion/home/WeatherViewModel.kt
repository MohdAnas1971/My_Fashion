package com.example.myfashion.presentasion.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfashion.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState

    private var lastLat: Double? = null
    private var lastLon: Double? = null


    fun loadWeather(lat: Double, lon: Double) {

        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            repository.getWeather(lat, lon)
                .onSuccess {
                    _uiState.value = WeatherUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = WeatherUiState.Error(
                        it.message ?: "Something went wrong"
                    )
                }
        }
    }

    // 🔁 Auto refresh every X minutes
    fun startAutoRefresh(intervalMinutes: Long = 10) {
        viewModelScope.launch {
            while (true) {
                delay(intervalMinutes * 60 * 1000)
                lastLat?.let { lat ->
                    lastLon?.let { lon ->
                        loadWeather(lat, lon)
                    }
                }
            }
        }
    }
}
