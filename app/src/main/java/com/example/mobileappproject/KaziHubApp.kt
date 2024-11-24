package com.example.mobileappproject

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobileappproject.api.RetrofitInstance
import com.example.mobileappproject.data.AppContainer
import com.example.mobileappproject.data.AppDataContainer
import com.example.mobileappproject.data.KaziHubDatabase
import com.example.mobileappproject.data.api.CategoryApi
import com.example.mobileappproject.data.apiRepositories.ApiCategoryRepository
import com.example.mobileappproject.data.apiRepositories.ApiFavouriteRepository
import com.example.mobileappproject.data.apiRepositories.ApiOrderRepository
import com.example.mobileappproject.data.apiRepositories.ApiServiceRepository
import com.example.mobileappproject.data.apiRepositories.ApiUserRepository
import com.example.mobileappproject.data.dao.CategoryDao
import com.example.mobileappproject.data.repositories.FavouriteRepository
import com.example.mobileappproject.data.repositories.OrderRepository
import com.example.mobileappproject.data.repositories.ServiceRepository
import com.example.mobileappproject.data.repositories.UserRepository
import com.example.mobileappproject.ui.AppViewModelProvider
import com.example.mobileappproject.ui.navigation.KaziHubNavHost
import com.example.mobileappproject.viewmodels.CategoriesViewModel
import com.example.mobileappproject.viewmodels.FavouriteViewModel
import com.example.mobileappproject.viewmodels.OrderViewModel
import com.example.mobileappproject.viewmodels.ServiceViewModel
import com.example.mobileappproject.viewmodels.UserViewModel
import android.content.Context
import androidx.compose.ui.platform.LocalContext


@Composable
fun KaziHubApp( navController: NavHostController = rememberNavController()) {

    val context: Context = LocalContext.current
    // Initialize AppContainer to provide repositories
    val appContainer: AppContainer = AppDataContainer(context)

    // Create a ViewModelProvider.Factory using repositories from AppContainer
    val appViewModelProvider = AppViewModelProvider(
        categoryRepository = appContainer.categoryRepository,
        favouriteRepository = appContainer.favouriteRepository,
        orderRepository = appContainer.orderRepository,
        serviceRepository = appContainer.serviceRepository,
        userRepository = appContainer.userRepository
    )

    // Get ViewModels with custom factory using viewModel()
    val categoriesViewModel: CategoriesViewModel = viewModel(factory = appViewModelProvider)
    val favouriteViewModel: FavouriteViewModel = viewModel(factory = appViewModelProvider)
    val orderViewModel: OrderViewModel = viewModel(factory = appViewModelProvider)
    val serviceViewModel: ServiceViewModel = viewModel(factory = appViewModelProvider)
    val userViewModel: UserViewModel = viewModel(factory = appViewModelProvider)

    // Use the navController for navigation within the app
    KaziHubNavHost(navController = navController)
}


/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaziHubTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            Row {
                // Shopping Cart Icon
                IconButton(onClick = onCartClick) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = stringResource(R.string.cart_icon)
                    )
                }
                // Menu Icon
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.menu_icon)
                    )
                }
            }
        }
    )
}
