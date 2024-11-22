package com.example.mobileappproject.ui.categories

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
import com.example.mobileappproject.data.CategoryItem
import com.example.mobileappproject.ui.components.AppDrawer
import com.example.mobileappproject.ui.navigation.NavigationDestination
import com.example.mobileappproject.ui.home.SearchBar
import kotlinx.coroutines.launch

object CategoriesDestination : NavigationDestination {
    override val route = "categories"
    override val titleRes = R.string.categories
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    categoriesViewModel: CategoriesViewModel = CategoriesViewModel(),
    navController: NavHostController,
    onCategoryClick: (String) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val groupedCategories = categoriesViewModel.categories.groupBy { it.category }

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
                    title = stringResource(R.string.categories),
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    navigateUp = { navController.popBackStack() },
                    onCartClick = { /* Handle Cart Click */ },
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
                    .padding(horizontal = 16.dp)
            ) {
                // Search Bar
                item {
                    SearchBar(modifier = Modifier.padding(horizontal = 16.dp))
                }

                // Grouped categories
                groupedCategories.forEach { (parentCategory, subCategories) ->
                    item {
                        CategoryCarousel(
                            category = parentCategory,
                            subItems = subCategories,
                            onCategoryClick = onCategoryClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCarousel(
    category: String,
    subItems: List<CategoryItem>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = category,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(subItems) { subItem ->
                CategoryCard(
                    name = subItem.name,
                    imageRes = subItem.imageRes,
                    onClick = { onCategoryClick(subItem.name) }
                )
            }
        }
    }
}

@Composable
fun CategoryCard(
    name: String,
    @DrawableRes imageRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(120.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
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





