package com.example.weatherapp

import retrofit2.Response

class WeatherRepository {

    // Calls the API using our RetrofitInstance
    suspend fun getWeather(city: String, apiKey: String): Response<WeatherResponse> {
        return RetrofitInstance.api.getWeatherByCity(cityName = city, apiKey = apiKey)
    }
}


