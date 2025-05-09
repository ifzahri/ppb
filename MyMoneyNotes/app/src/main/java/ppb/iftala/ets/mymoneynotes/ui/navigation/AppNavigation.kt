package ppb.iftala.ets.mymoneynotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ppb.iftala.ets.mymoneynotes.ui.screens.AddTransactionScreen
import ppb.iftala.ets.mymoneynotes.ui.screens.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddTransaction : Screen("add_transaction")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToAddTransaction = {
                    navController.navigate(Screen.AddTransaction.route)
                }
            )
        }

        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}