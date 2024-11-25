package com.example.mobileappproject.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobileappproject.R
import com.example.mobileappproject.RegisTopAppBar
import com.example.mobileappproject.ui.navigation.NavigationDestination

object ShoppingCartDestination : NavigationDestination {
    override val route = "shoppingCart"
    override val titleRes = R.string.cart_icon
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShoppingCartScreen(
    navController: NavHostController,

) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RegisTopAppBar(
                title = stringResource(R.string.cart_icon),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navController = navController
            )
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your shopping cart is empty.",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ShoppingCartScreenPreview() {
    //ShoppingCartScreen()
}
