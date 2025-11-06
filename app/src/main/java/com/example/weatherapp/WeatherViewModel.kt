package com.example.weatherapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import android.util.Log

class WeatherViewModel : ViewModel() {

    // Repository handles all API calls
    private val repository = WeatherRepository()

    // Reactive state variables for the UI
    var cityName by mutableStateOf("")          // city entered by the user
    var weatherText by mutableStateOf("")       // weather result text
    var isLoading by mutableStateOf(false)      // loading indicator for progress circle

    /**
     * Fetch weather by city name
     */
    fun fetchWeather(apiKey: String) {
        if (cityName.isBlank()) {
            weatherText = "Please enter a city name!"
            return
        }

        // Coroutine for background network work
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

    /**
     * Fetch weather using current GPS coordinates
     */
    fun fetchWeatherByLocation(context: Context, apiKey: String) {
        Log.d("WeatherApp", "fetchWeatherByLocation() started")
        viewModelScope.launch {
            isLoading = true

            // Use the helper class to get last known location
            val helper = LocationHelper(context)
            val location = helper.getLastLocation()

            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude

                // Call the Retrofit endpoint for coordinates
                val response = RetrofitInstance.api.getWeatherByCoordinates(lat, lon, apiKey)

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
            } else {
                weatherText = "Unable to detect location."
            }

            isLoading = false
        }
    }
}
