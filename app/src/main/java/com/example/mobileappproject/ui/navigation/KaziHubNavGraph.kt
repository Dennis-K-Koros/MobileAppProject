package com.example.mobileappproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileappproject.data.AppDataContainer
import com.example.mobileappproject.data.repositories.UserRepository
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
import com.example.mobileappproject.viewmodels.CategoriesViewModelFactory
import com.example.mobileappproject.viewmodels.ServiceViewModel
import com.example.mobileappproject.viewmodels.ServiceViewModelFactory
import com.example.mobileappproject.viewmodels.UserViewModel
import com.example.mobileappproject.viewmodels.UserViewModelFactory


@Composable
fun KaziHubNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){

//    val appContainer = (LocalContext.current.applicationContext as? KaziHubApplication)?.container
//        ?: throw IllegalStateException("Application is not KaziHubApplication")
     val appContainer = AppDataContainer(LocalContext.current)


    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDestination.route){

            val userViewModel : UserViewModel = viewModel(factory = UserViewModelFactory(appContainer.userRepository))
            val serviceViewModel: ServiceViewModel = viewModel(factory = ServiceViewModelFactory(appContainer.serviceRepository))
            val categoriesViewModel: CategoriesViewModel = viewModel(factory = CategoriesViewModelFactory(appContainer.categoryRepository))

            HomeScreen(
                navController = navController,
                userViewModel = userViewModel,
                serviceViewModel = serviceViewModel,
                categoriesViewModel = categoriesViewModel
            )
        }
        composable(route = CategoriesDestination.route){

            val categoriesViewModel: CategoriesViewModel = viewModel(factory = CategoriesViewModelFactory(appContainer.categoryRepository))
            val userViewModel : UserViewModel = viewModel(factory = UserViewModelFactory(appContainer.userRepository))

            CategoriesScreen(
                navController = navController,
                userViewModel = userViewModel,
                categoriesViewModel = categoriesViewModel,
                onSubcategoryClick = { /* Handle Category Click */ },
            )
        }
        composable(route = FavouritesDestination.route){

            val userViewModel : UserViewModel = viewModel(factory = UserViewModelFactory(appContainer.userRepository))

            FavoritesScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }
        composable(route = ServicesDestination.route){

            val userViewModel : UserViewModel = viewModel(factory = UserViewModelFactory(appContainer.userRepository))
            val serviceViewModel: ServiceViewModel = viewModel(factory = ServiceViewModelFactory(appContainer.serviceRepository))

            ServicesScreen(
                navController = navController,
                userViewModel = userViewModel,
                serviceViewModel = serviceViewModel
            )
        }
        composable(route = OrdersDestination.route){

            val userViewModel : UserViewModel = viewModel(factory = UserViewModelFactory(appContainer.userRepository))

            OrdersScreen(
                navController = navController,
                userViewModel = userViewModel
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