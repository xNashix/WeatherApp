package com.example.weatherapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainPagerScreen(viewModel: WeatherViewModel = viewModel()) {
    val pagerState = rememberPagerState(initialPage = 0)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HorizontalPager(count = 2, state = pagerState, modifier = Modifier.weight(1f)) { page ->
            when (page) {
                0 -> CurrentLocationPage(viewModel, context)
                1 -> SearchPage(viewModel)
            }
        }

        // little indicator dots at the bottom
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }
}

@Composable
fun CurrentLocationPage(viewModel: WeatherViewModel, context: android.content.Context) {
    val result = viewModel.weatherText
    val loading = viewModel.isLoading

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Weather App 🌦️", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            viewModel.fetchWeatherByLocation(context, "fc65ce837195b1e3c4bf5274e681280a")
        }) {
            Text("Use My Location")
        }

        Spacer(Modifier.height(16.dp))

        if (loading) CircularProgressIndicator()
        else Text(result, style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(24.dp))
        Text("← Swipe left to search", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SearchPage(viewModel: WeatherViewModel) {
    val city = viewModel.cityName
    val result = viewModel.weatherText
    val loading = viewModel.isLoading

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Search Weather 🔍", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { viewModel.cityName = it },
            label = { Text("Enter city name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            viewModel.fetchWeather("fc65ce837195b1e3c4bf5274e681280a")
        }) {
            Text("Get Weather")
        }

        Spacer(Modifier.height(16.dp))

        if (loading) CircularProgressIndicator()
        else Text(result, style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(24.dp))
        Text("→ Swipe right for current location", style = MaterialTheme.typography.bodyMedium)
    }
}
