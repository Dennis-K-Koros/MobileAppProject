package com.example.mobileappproject.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mobileappproject.KaziHubTopAppBar
import com.example.mobileappproject.R
import com.example.mobileappproject.data.entities.Category
import com.example.mobileappproject.data.entities.Service
import com.example.mobileappproject.ui.components.AppDrawer
import com.example.mobileappproject.ui.navigation.NavigationDestination
import com.example.mobileappproject.viewmodels.CategoriesViewModel
import com.example.mobileappproject.viewmodels.ServiceViewModel
import com.example.mobileappproject.viewmodels.UserViewModel
import kotlinx.coroutines.launch


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    serviceViewModel: ServiceViewModel,
    categoriesViewModel: CategoriesViewModel,
    userViewModel: UserViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Fetch Data from the API
    LaunchedEffect(Unit) {
        categoriesViewModel.fetchCategoriesFromApi()
        serviceViewModel.fetchServices()
    }

    // Collect data from ViewModels
    val services by serviceViewModel.services.collectAsState()
    val categories by categoriesViewModel.categories.collectAsState()

    val isLoadingCategories by categoriesViewModel.isLoading.collectAsState()
    val isLoadingServices by serviceViewModel.isLoading.collectAsState()

    AppDrawer(
        userViewModel = userViewModel,
        navController = navController,
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                KaziHubTopAppBar(
                    title = stringResource(R.string.app_name),
                    canNavigateBack = false,
                    scrollBehavior = scrollBehavior,
                    onCartClick = { /* Handle Cart Click */ },
                    onMenuClick = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // Show loading indicator while data is loading
                if (isLoadingCategories || isLoadingServices) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                } else {
                    // Search Bar Section
                    item {
                        SearchBar(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                    // Categories Carousel Section
                    item {
                        CategoryCarousel(categories = categories)
                    }

                    // Service List Section
                    services.groupBy { it.subcategory }.forEach { (subcategory, services) ->
                        item {
                            ServiceCategoryCarousel(category = subcategory, services = services)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun CategoryCarousel(categories: List<Category>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable { /* Handle category click */ }
            )
        }
    }
}

@Composable
fun ServiceCategoryCarousel(category: String, services: List<Service>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = category,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp)
        )

        if (services.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(services, key = { "${category}_${it.id}_${it.hashCode()}" }) { service ->
                    ServiceHomeCard(service)
                }
            }
        } else {
            Text(
                text = stringResource(R.string.no_services),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun ServiceHomeCard(service: Service, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .size(150.dp)
            .clickable { /* Handle card click */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            if (service.image != null) {
                AsyncImage(
                    model = service.image,
                    contentDescription = service.serviceName,
                    modifier = Modifier.size(48.dp)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.service_placeholder),
                    contentDescription = service.serviceName,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = service.serviceName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}



