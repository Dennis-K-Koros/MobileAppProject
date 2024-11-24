package com.example.mobileappproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileappproject.data.repositories.CategoryRepository
import com.example.mobileappproject.ui.screens.CategoriesDestination
import com.example.mobileappproject.ui.screens.FavouritesDestination
import com.example.mobileappproject.ui.screens.HomeDestination
import com.example.mobileappproject.ui.screens.HomeScreen
import com.example.mobileappproject.ui.registration.LogInDestination
import com.example.mobileappproject.ui.screens.OrdersDestination
import com.example.mobileappproject.ui.registration.ResetPasswordDestination
import com.example.mobileappproject.ui.screens.ServicesDestination
import com.example.mobileappproject.ui.registration.SignUpDestination
import com.example.mobileappproject.ui.screens.CategoriesScreen
import com.example.mobileappproject.ui.screens.FavoritesScreen
import com.example.mobileappproject.ui.screens.OrdersScreen
import com.example.mobileappproject.ui.screens.ServicesScreen
import com.example.mobileappproject.viewmodels.CategoriesViewModel
import com.example.mobileappproject.viewmodels.ServiceViewModel
import com.example.mobileappproject.viewmodels.UserViewModel


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

            val userViewModel: UserViewModel = viewModel()
            val serviceViewModel: ServiceViewModel = viewModel()
            val categoriesViewModel: CategoriesViewModel = viewModel()

            HomeScreen(
                navController = navController,
                userViewModel = userViewModel,
                serviceViewModel = serviceViewModel,
                categoriesViewModel = categoriesViewModel
            )
        }
        composable(route = CategoriesDestination.route){

            val categoriesViewModel: CategoriesViewModel = viewModel()
            val userViewModel: UserViewModel = viewModel()

            CategoriesScreen(
                navController = navController,
                userViewModel = userViewModel,
                categoriesViewModel = categoriesViewModel,
                onSubcategoryClick = { /* Handle Category Click */ },
            )
        }
        composable(route = FavouritesDestination.route){
            FavoritesScreen(
                navController = navController,
            )
        }
        composable(route = ServicesDestination.route){

            val userViewModel: UserViewModel = viewModel()
            val serviceViewModel: ServiceViewModel = viewModel()

            ServicesScreen(
                navController = navController,
                userViewModel = userViewModel,
                serviceViewModel = serviceViewModel
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