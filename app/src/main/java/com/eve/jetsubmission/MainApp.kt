package com.eve.jetsubmission

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eve.jetsubmission.ui.navigation.NavigationItem
import com.eve.jetsubmission.ui.navigation.Screen
import com.eve.jetsubmission.ui.sreen.about.AboutScreen
import com.eve.jetsubmission.ui.sreen.cart.CartScreen
import com.eve.jetsubmission.ui.sreen.confirm.ConfirmScreen
import com.eve.jetsubmission.ui.sreen.detail.DetailScreen
import com.eve.jetsubmission.ui.sreen.product.ProductScreen
import com.eve.jetsubmission.ui.theme.JetSubmissionTheme
import com.eve.jetsubmission.utils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route && currentRoute != Screen.Confirm.route) {
                BottomBar(navHostController = navHostController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = Screen.Product.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Product.route) {
                ProductScreen(
                    navigateToDetail = { productId ->
                        navHostController.navigate(Screen.Detail.createRoute(productId))
                    }
                )
            }
            composable(Screen.Cart.route) {
                val context = LocalContext.current
                CartScreen(
                    onOrderButtonClicked = {
                        navHostController.navigate(Screen.Confirm.route)
                    },
                    navigateToDetail = { productId ->
                        navHostController.navigate(Screen.Detail.createRoute(productId))
                    },
                    onShareClicked = { message ->
                        shareOrder(context, message)
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.IntType
                    }
                )
            ) {
                val id = it.arguments?.getInt("productId") ?: 0
                val context = LocalContext.current
                DetailScreen(
                    productId = id,
                    navigationBack = { navHostController.navigateUp() },
                    navigationToCart = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Cart.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onShareClicked = { message ->
                        shareOrder(context, message)
                    }
                )
            }
            composable(route = Screen.Confirm.route) {
                val context = LocalContext.current
                ConfirmScreen(
                    onBackClick = {
                        navHostController.navigateUp()
                    },
                    onPayOrderClicked = { message ->
                        coroutineScope.launch {
                            context.showToast(message)
                            delay(500)
                            navHostController.popBackStack()
                            navHostController.navigate(Screen.Product.route) {
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier,
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_product),
                icon = Icons.Default.Home,
                screen = Screen.Product
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )
        BottomNavigation(
            modifier = Modifier.background(Color.White)
        ) {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navHostController.navigate(item.screen.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

private fun shareOrder(
    context: Context,
    summary: String
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_SUBJECT,
            "Discount Home Guitar"
        )
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            "Discount Home Guitar"
        )
    )
}

@Preview
@Composable
fun MainAppPreview() {
    JetSubmissionTheme {
        MainApp()
    }
}