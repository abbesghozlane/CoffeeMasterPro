package com.coffeemaster.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coffeemaster.app.ui.screens.ReportsScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onNavigate = { route -> navController.navigate(route) })
        }
    composable("sales") { SalesScreenReal() }
        composable("inventory") { InventoryScreen() }
        composable("employees") { EmployeesScreen() }
    composable("capital") { CapitalScreenReal() }
        composable("reports") {
            // Temporary user placeholder
            val user = com.coffeemaster.app.models.User(
                id = "u1",
                name = "Owner",
                email = "owner@example.com",
                phone = "",
                role = com.coffeemaster.app.models.UserRole.OWNER
            )
            ReportsScreen(onBack = { navController.popBackStack() }, currentUser = user)
        }
    }
}
