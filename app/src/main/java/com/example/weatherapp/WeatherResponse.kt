package com.example.weatherapp

// Represents the main section of the JSON (temperature, humidity)
data class MainInfo(
    val temp: Double,
    val humidity: Int
)

// Represents one element of the weather array (description, icon)
data class WeatherDescription(
    val description: String,
    val icon: String
)

// Object that holds everything
data class WeatherResponse(
    val name: String,                     // city name
    val main: MainInfo,                   // nested main object
    val weather: List<WeatherDescription> // list of weather info
)
