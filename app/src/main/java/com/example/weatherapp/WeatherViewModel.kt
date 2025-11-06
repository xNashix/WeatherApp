package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    // UI variables
    var cityName by mutableStateOf("")
    var weatherText by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    // Function to fetch weather
    fun fetchWeather(apiKey: String) {
        if (cityName.isBlank()) {
            weatherText = "Please enter a city name!"
            return
        }

        // Launch a background thread
        viewModelScope.launch {
            isLoading = true
            val response = repository.getWeather(cityName, apiKey)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val desc = body.weather.firstOrNull()?.description ?: "Unknown"
                    val temp = body.main.temp
                    weatherText = "${body.name}: $temp°C, $desc"
                } else {
                    weatherText = "No data received."
                }
            } else {
                weatherText = "Error: ${response.message()}"
            }
            isLoading = false
        }
    }
}
