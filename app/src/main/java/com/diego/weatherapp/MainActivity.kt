package com.diego.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.diego.weatherapp.ui.navigation.WeatherNavHost
import com.diego.weatherapp.ui.theme.WeatherappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherappTheme {
                WeatherNavHost()
            }
        }
    }
}
