package com.roblesdotdev.ohmylist.navigation

import androidx.navigation.NavHostController

sealed class NavDestination(
    val route: String,
) {
    data object ShopList : NavDestination(route = "shopList")
    data object ShopListDetail : NavDestination(route = "shopListDetail/{listId}")
    data object AddShopList : NavDestination(route = "addShopList?listId={listId}")
}

class ShopListNavActions(private val navController: NavHostController) {
    fun navigateToShopListDetail(
        listId: Int,
        popUp: Boolean = false,
    ) {
        navController.navigate(route = "shopListDetail/$listId") {
            if (popUp) {
                popUpTo("addShopList") {
                    inclusive = true
                }
            }
        }
    }

    fun navigateToAddShopList(listId: String? = null) {
        navController.navigate(route = "addShopList?listId=$listId") {
            if (listId != null) {
                popUpTo("shopList")
            }
        }
    }
}
