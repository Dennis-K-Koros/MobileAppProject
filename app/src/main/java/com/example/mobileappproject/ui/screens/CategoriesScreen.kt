package com.example.mobileappproject.ui.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.mobileappproject.data.entities.Subcategory
import com.example.mobileappproject.ui.components.AppDrawer
import com.example.mobileappproject.ui.navigation.NavigationDestination
import com.example.mobileappproject.viewmodels.CategoriesViewModel
import com.example.mobileappproject.viewmodels.UserViewModel
import kotlinx.coroutines.launch

object CategoriesDestination : NavigationDestination {
    override val route = "categories"
    override val titleRes = R.string.categories
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavHostController,
    categoriesViewModel: CategoriesViewModel,
    userViewModel: UserViewModel,
    onSubcategoryClick: (String) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val categories by categoriesViewModel.categories.collectAsState()
    val isLoadingCategories by categoriesViewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        categoriesViewModel.fetchCategoriesFromApi()
        categoriesViewModel.categories.collect { data ->
            Log.d("CategoriesData", data.toString())
        }
    }

    val groupedCategories = categories.groupBy { it.name }

    AppDrawer(
        userViewModel = userViewModel,
        navController = navController,
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                KaziHubTopAppBar(
                    title = stringResource(R.string.categories),
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    navigateUp = { navController.popBackStack() },
                    onCartClick = {
                        navController.navigate("shoppingCart")
                    },
                    onMenuClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(vertical = 8.dp)
            ) {
                if (isLoadingCategories) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                } else {
                    groupedCategories.forEach { (categoryName, categories) ->
                        item {
                            Text(
                                text = categoryName,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        item {
                            val allSubcategories = categories
                                .flatMap { it.subcategories.split(",") } // Split subcategories string into a list
                                .map { name -> Subcategory(name = name.trim(), id = "") } // Create Subcategory objects for UI

                            SubcategoriesCarousel(
                                subcategories = allSubcategories,
                                onSubcategoryClick = onSubcategoryClick
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SubcategoriesCarousel(
    subcategories: List<Subcategory>,
    onSubcategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 25.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(subcategories) { subcategory ->
            SubcategoryCard(
                name = subcategory.name ?: "Unknown", // Ensure `name` is not null
                imageRes = R.drawable.service_placeholder, // Placeholder image
                onClick = { onSubcategoryClick(subcategory.name ?: "Unknown") } // Handle null values gracefully
            )
        }
    }
}





@Composable
fun SubcategoryCard(
    name: String,
    @DrawableRes imageRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Makes the card square
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = name,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

