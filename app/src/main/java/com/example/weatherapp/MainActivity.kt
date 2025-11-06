package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.util.Log

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// The main entry point of the app.
// ComponentActivity = base class for activities using Jetpack Compose.
class MainActivity : ComponentActivity() {

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ask for location permission at runtime
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
        // setContent = this replaces XML layout inflation.
        setContent {
            // Create a navigation controller to manage moving between screens
            val navController = rememberNavController()

            NavHost(
                navController = navController, // controls navigation actions
                startDestination = "splash" // which screen shows first when the app launches -> start on the SplashScreen
            ) {
                // Define composable destinations (screens)
                composable("splash") { SplashScreen(navController) } // first screen
                composable("main") { WeatherApp() } // main weather screen
            }
        }
    }
}

@Composable
fun WeatherApp(viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val city = viewModel.cityName
    val result = viewModel.weatherText
    val loading = viewModel.isLoading
    val context = LocalContext.current

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
                text = "Weather Search 🌦️",
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
                    // API key here
                    viewModel.fetchWeather("fc65ce837195b1e3c4bf5274e681280a")
                },
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Get Weather")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    Log.d("WeatherApp", "Button pressed for location fetch") // ✅
                    viewModel.fetchWeatherByLocation(context, "fc65ce837195b1e3c4bf5274e681280a")
                },
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Use My Location")
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