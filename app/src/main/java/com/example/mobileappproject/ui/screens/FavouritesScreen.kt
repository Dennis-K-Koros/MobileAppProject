package com.example.mobileappproject.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobileappproject.KaziHubTopAppBar
import com.example.mobileappproject.R
import com.example.mobileappproject.ui.components.AppDrawer
import com.example.mobileappproject.ui.navigation.NavigationDestination
import com.example.mobileappproject.viewmodels.UserViewModel
import kotlinx.coroutines.launch


object FavouritesDestination : NavigationDestination {
    override val route = "favourites"
    override val titleRes = R.string.favourites
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
) {
    val tabs = listOf("Services", "Products")
    val selectedIndex = remember { mutableStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    AppDrawer(
        navController = navController,
        userViewModel = userViewModel,
        drawerState = drawerState // Pass drawer state
    ){
        Scaffold (
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                KaziHubTopAppBar(
                    title = stringResource(R.string.favourites),
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    navigateUp = { navController.popBackStack() },
                    onCartClick = {
                        navController.navigate("shoppingCart")
                    },
                    onMenuClick = {
                        coroutineScope.launch {
                            drawerState.open() // Open the drawer when the menu is clicked
                        }
                    }
                )
            }
        ){innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                // Tabs
                TabRow(selectedTabIndex = selectedIndex.value) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedIndex.value == index,
                            onClick = { selectedIndex.value = index },
                            text = { Text(text = title) }
                        )
                    }
                }

                // Content
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when (selectedIndex.value) {
                        0 -> Text(
                            text = "No Favorite Services",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        1 -> Text(
                            text = "No Favorite Products",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    }


}



