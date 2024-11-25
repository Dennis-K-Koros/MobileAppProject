package com.example.mobileappproject.ui.screens


import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mobileappproject.KaziHubTopAppBar
import com.example.mobileappproject.R
import com.example.mobileappproject.data.ServiceItem
import com.example.mobileappproject.data.entities.Service
import com.example.mobileappproject.ui.components.AppDrawer
import com.example.mobileappproject.ui.navigation.NavigationDestination
import com.example.mobileappproject.viewmodels.ServiceViewModel
import com.example.mobileappproject.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object ServicesDestination : NavigationDestination {
    override val route = "services"
    override val titleRes = R.string.services
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    userViewModel: UserViewModel,
    serviceViewModel: ServiceViewModel,
    navController: NavHostController,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val services by serviceViewModel.services.collectAsState()
    val isLoadingServices by serviceViewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        serviceViewModel.fetchServices()
        serviceViewModel.services.collect { data ->
            Log.d("ServicesData", data.toString())
        }
    }

    AppDrawer(
        userViewModel = userViewModel,
        navController = navController,
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                KaziHubTopAppBar(
                    title = stringResource(R.string.services),
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    navigateUp = { navController.popBackStack() },
                    onCartClick = { /* Handle Cart Click */ },
                    onMenuClick = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {// Show loading indicator while data is loading
                if (isLoadingServices) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                } else {
                    // Search bar
                    item {
                        SearchBar(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                    // Services List Grouped by Category
                    services.groupBy { it.subcategory }.forEach { (subcategory, services) ->
                        item {
                            ServiceCarousel(category = subcategory, services = services)
                        }
                    }
                }
            }
            }
        }
    }


    @Composable
    fun ServiceCarousel(category: String, services: List<Service>, modifier: Modifier = Modifier) {
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
    fun ServiceCard(service: Service, modifier: Modifier = Modifier) {
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

