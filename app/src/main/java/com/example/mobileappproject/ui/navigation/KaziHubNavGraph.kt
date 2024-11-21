package com.example.mobileappproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileappproject.ui.categories.CategoriesDestination
import com.example.mobileappproject.ui.screens.FavouritesDestination
import com.example.mobileappproject.ui.home.HomeDestination
import com.example.mobileappproject.ui.home.HomeScreen
import com.example.mobileappproject.ui.registration.LogInDestination
import com.example.mobileappproject.ui.screens.OrdersDestination
import com.example.mobileappproject.ui.registration.ResetPasswordDestination
import com.example.mobileappproject.ui.screens.ServicesDestination
import com.example.mobileappproject.ui.registration.SignUpDestination
import com.example.mobileappproject.ui.categories.CategoriesScreen
import com.example.mobileappproject.ui.screens.FavoritesScreen
import com.example.mobileappproject.ui.screens.OrdersScreen
import com.example.mobileappproject.ui.screens.ServicesScreen


@Composable
fun KaziHubNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDestination.route){
            HomeScreen(
                navController = navController,
            )
        }
        composable(route = CategoriesDestination.route){
            CategoriesScreen(
                navController = navController,
                onCategoryClick = { /* Handle Category Click */ },
            )
        }
        composable(route = FavouritesDestination.route){
            FavoritesScreen(
                navController = navController,
            )
        }
        composable(route = ServicesDestination.route){
            ServicesScreen(
                navController = navController,
                onServiceClick = { /* Handle Service Click */ },
            )
        }
        composable(route = OrdersDestination.route){
            OrdersScreen(
                navController = navController,
            )
        }
        composable(route = SignUpDestination.route){

        }
        composable(route = LogInDestination.route){

        }
        composable(route = ResetPasswordDestination.route){

        }
    }
}