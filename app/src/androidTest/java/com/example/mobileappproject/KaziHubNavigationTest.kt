package com.example.mobileappproject


import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.mobileappproject.ui.navigation.KaziHubNavHost
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class KaziHubNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupKaziHubNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            KaziHubNavHost(navController = navController)
        }
    }

    @Test
    fun kaziHubNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName("home") // Verify start destination
    }

    @Test
    fun kaziHubNavHost_navigateToCategoriesScreen() {
        composeTestRule.onNodeWithText("Categories").performClick()
        navController.assertCurrentRouteName("categories")
    }

    @Test
    fun kaziHubNavHost_navigateToFavouritesScreen() {
        composeTestRule.onNodeWithText("Favourites").performClick()
        navController.assertCurrentRouteName("favourites")
    }

    @Test
    fun kaziHubNavHost_navigateToServicesScreen() {
        composeTestRule.onNodeWithText("Services").performClick()
        navController.assertCurrentRouteName("services")
    }

    @Test
    fun kaziHubNavHost_navigateToOrdersScreen() {
        composeTestRule.onNodeWithText("Orders").performClick()
        navController.assertCurrentRouteName("orders")
    }

    @Test
    fun kaziHubNavHost_navigateToProfileScreen() {
        composeTestRule.onNodeWithText("Profile").performClick()
        navController.assertCurrentRouteName("profile")
    }

    @Test
    fun kaziHubNavHost_navigateToShoppingCartScreen() {
        composeTestRule.onNodeWithText("Shopping Cart").performClick()
        navController.assertCurrentRouteName("shopping_cart")
    }

    @Test
    fun kaziHubNavHost_navigateToLogInScreen() {
        composeTestRule.onNodeWithText("Log In").performClick()
        navController.assertCurrentRouteName("login")
    }

    @Test
    fun kaziHubNavHost_navigateToSignUpScreen() {
        composeTestRule.onNodeWithText("Sign Up").performClick()
        navController.assertCurrentRouteName("signup")
    }
}
