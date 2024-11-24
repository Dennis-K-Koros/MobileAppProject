package com.example.mobileappproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobileappproject.KaziHubTopAppBar
import com.example.mobileappproject.R
import com.example.mobileappproject.ui.components.AppDrawer
import com.example.mobileappproject.ui.navigation.NavigationDestination
import com.example.mobileappproject.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object OrdersDestination : NavigationDestination {
    override val route = "orders"
    override val titleRes = R.string.orders
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val tabs = listOf("Current", "Closed")
    val selectedIndex = remember { mutableStateOf(0) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Simulating API data
    val orders = remember { mutableStateOf<List<Map<String, String>>?>(null) }
    val isLoggedIn = userViewModel.isLoggedIn?: false // Provide a default value if null


    AppDrawer(
        navController = navController,
        userViewModel = userViewModel,
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
            Box(modifier = Modifier.padding(innerPadding)) {
                if (isLoggedIn != true) {
                    // Show Login/Signup buttons if not logged in
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { navController.navigate("login") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            Text(text = "Login")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate("signup") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            Text(text = "Signup")
                        }
                    }
                } else {
                    // Tabs
                    Column(modifier = Modifier.fillMaxSize()) {
                        TabRow(selectedTabIndex = selectedIndex.value) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedIndex.value == index,
                                    onClick = { selectedIndex.value = index },
                                    text = { Text(text = title) }
                                )
                            }
                        }

                        // Content based on tab
                        val currentOrders = orders.value?.filter { it["status"] != "closed" } ?: emptyList()
                        val closedOrders = orders.value?.filter { it["status"] == "closed" } ?: emptyList()

                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            when (selectedIndex.value) {
                                0 -> {
                                    if (currentOrders.isEmpty()) {
                                        item {
                                            Text(
                                                text = "No Current Orders",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.fillMaxWidth().padding(16.dp)
                                            )
                                        }
                                    } else {
                                        items(currentOrders.size) { index ->
                                            OrderCard(order = currentOrders[index])
                                        }
                                    }
                                }
                                1 -> {
                                    if (closedOrders.isEmpty()) {
                                        item {
                                            Text(
                                                text = "No Closed Orders",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.fillMaxWidth().padding(16.dp)
                                            )
                                        }
                                    } else {
                                        items(closedOrders.size) { index ->
                                            OrderCard(order = closedOrders[index])
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Map<String, String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Start Date: ${order["orderDate"]}", fontSize = 14.sp)
            Text(text = "Completion Date: ${order["updatedAt"]}", fontSize = 14.sp)
            Text(
                text = "Status: ${order["status"]?.capitalize()}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (order["status"] == "closed") Color.Green else Color.Red
            )
        }
    }
}
