package com.eve.jetsubmission.ui.navigation

sealed class Screen(val route: String) {
    object Product : Screen("product")
    object Cart : Screen("cart")
    object About : Screen("about")
    object Confirm : Screen("confirm")
    object Detail : Screen("product/{productId}") {
        fun createRoute(productId: Int) = "product/$productId"
    }
}
