package com.example.mobileappproject.ui.screens


import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.mobileappproject.ui.home.HomeViewModel
import com.example.mobileappproject.ui.home.SearchBar
import com.example.mobileappproject.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ServicesDestination : NavigationDestination {
    override val route = "services"
    override val titleRes = R.string.services
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    homeViewModel: HomeViewModel = HomeViewModel(),
    navController: NavHostController,
    onServiceClick: (String) -> Unit
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
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                KaziHubTopAppBar(
                    title = stringResource(R.string.app_name),
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
            ) {


                // Search bar
                item {
                    SearchBar(modifier = Modifier.padding(horizontal = 16.dp))
                }

                // Services List Grouped by Category
                items(groupedServices.keys.toList()) { category ->
                    val services = groupedServices[category].orEmpty()
                    ServiceCarousel(
                        category = category,
                        services = services,
                        onServiceClick = onServiceClick,
                    )
                }
            }
        }
    }
}




@Composable
fun ServiceCarousel(
    category: String,
    services: List<ServiceItem>, // Replace with your data class name
    onServiceClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        // Category Title
        Text(
            text = category,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp)
        )

        // Horizontal Row of Services
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(services) { service ->
                ServiceCard(
                    name = service.name,
                    imageRes = service.imageRes, // Replace with correct field
                    onClick = { onServiceClick(service.name) }
                )
            }
        }
    }
}

@Composable
fun ServiceCard(
    name: String,
    @DrawableRes imageRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(120.dp)
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(imageRes),
            contentDescription = name,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}
