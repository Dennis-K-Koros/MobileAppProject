package com.example.mobileappproject.ui.home

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
import com.example.mobileappproject.KaziHubTopAppBar
import com.example.mobileappproject.R
import com.example.mobileappproject.data.ServiceItem
import com.example.mobileappproject.ui.components.AppDrawer
import com.example.mobileappproject.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = HomeViewModel(), // Pass the ViewModel instance
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val groupedServices = homeViewModel.services.groupBy { it.category }

    AppDrawer(
        isLoggedIn = true,
        userName = stringResource(R.string.name),
        userEmail = stringResource(R.string.email),
        navController = navController,
        drawerState = drawerState // Pass drawer state
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
                        coroutineScope.launch {
                            drawerState.open() // Open the drawer when the menu is clicked
                        }
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
                // Search Bar Section
                item {
                    SearchBar(modifier = Modifier.padding(horizontal = 16.dp))
                }

                // Service Categories Section
                groupedServices.forEach { (category, services) ->
                    item {
                        ServiceCategoryCarousel(category = category, services = services)
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
fun ServiceCategoryCarousel(category: String, services: List<ServiceItem>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = category,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(services) { service ->
                ServiceCard(service)
            }
        }
    }
}

@Composable
fun ServiceCard(service: ServiceItem, modifier: Modifier = Modifier) {
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
            Icon(
                painter = painterResource(service.imageRes),
                contentDescription = service.name,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = service.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


