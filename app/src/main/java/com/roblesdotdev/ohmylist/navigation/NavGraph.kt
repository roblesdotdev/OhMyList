package com.roblesdotdev.ohmylist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.roblesdotdev.ohmylist.addShoplist.ui.AddShopListScreen
import com.roblesdotdev.ohmylist.addShoplist.ui.AddShopListViewModel
import com.roblesdotdev.ohmylist.shoplist.ui.ShopListScreen
import com.roblesdotdev.ohmylist.shoplist.ui.ShopListViewModel
import com.roblesdotdev.ohmylist.shoplistDetail.ui.ShopListDetailScreen
import com.roblesdotdev.ohmylist.shoplistDetail.ui.ShopListDetailViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: NavDestination,
    navActions: ShopListNavActions =
        remember(navController) {
            ShopListNavActions(navController)
        },
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
    ) {
        composable(
            NavDestination.ShopList.route,
        ) {
            val vm: ShopListViewModel = hiltViewModel()
            val state by vm.state.collectAsState()
            ShopListScreen(
                state = state,
                onAddClick = {
                    navActions.navigateToAddShopList()
                },
                onListClick = { listId ->
                    navActions.navigateToShopListDetail(listId)
                },
            )
        }

        composable(
            NavDestination.ShopListDetail.route,
            arguments =
                listOf(
                    navArgument("listId") {
                        type = NavType.IntType
                    },
                ),
        ) {
            val vm: ShopListDetailViewModel = hiltViewModel()
            val state by vm.state.collectAsState()
            ShopListDetailScreen(
                state = state,
                onEvent = vm::onEvent,
                onBack = { navController.popBackStack() },
                onEdit = { listId ->
                    navActions.navigateToAddShopList(listId.toString())
                },
            )
        }

        composable(
            NavDestination.AddShopList.route,
            arguments =
                listOf(
                    navArgument("listId") {
                        type = NavType.StringType
                        nullable = true
                    },
                ),
        ) {
            val vm: AddShopListViewModel = hiltViewModel()
            val state by vm.state.collectAsState()
            AddShopListScreen(
                state = state,
                onEvent = vm::onEvent,
                onListSaved = { listId ->
                    navActions.navigateToShopListDetail(listId, popUp = true)
                },
                onBack = { navController.popBackStack() },
            )
        }
    }
}
