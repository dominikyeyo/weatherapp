package com.diego.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.diego.weatherapp.feature.detail.presentation.DetailScreen
import com.diego.weatherapp.feature.search.presentation.SearchScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun WeatherNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {
        composable(route = Routes.SEARCH) {
            SearchScreen(
                onNavigateToDetail = { query ->
                    val encoded = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
                    navController.navigate("${Routes.DETAIL}/$encoded")
                }
            )
        }

        composable(
            route = "${Routes.DETAIL}/{query}",
            arguments = listOf(navArgument("query") { type = NavType.StringType })
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query").orEmpty()

            DetailScreen(
                q = query,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
