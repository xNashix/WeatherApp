package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
                composable("main") { MainPagerScreen()} // main weather screen
            }
        }
    }
}

