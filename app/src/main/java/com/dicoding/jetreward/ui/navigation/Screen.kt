package com.dicoding.jetreward.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailBook : Screen("home/{foodId}") {
        fun createRoute(foodId: Long) = "home/$foodId"
    }
}
