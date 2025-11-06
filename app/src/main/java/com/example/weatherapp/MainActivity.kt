package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "splash"
            ) {
                composable("splash") { SplashScreen(navController) }
                composable("main") { WeatherApp() }
            }
        }
    }
}

@Composable
fun WeatherApp(viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val city = viewModel.cityName
    val result = viewModel.weatherText
    val loading = viewModel.isLoading

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Weather Search",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = city,
                onValueChange = { viewModel.cityName = it },
                label = { Text("Enter city name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.fetchWeather("fc65ce837195b1e3c4bf5274e681280a")
                },
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Get Weather")
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (loading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = result,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewWeatherApp() {
    WeatherApp()
}
