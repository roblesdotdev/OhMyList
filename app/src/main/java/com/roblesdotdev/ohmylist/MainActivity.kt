package com.roblesdotdev.ohmylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.roblesdotdev.ohmylist.core.ui.theme.OhMyListTheme
import com.roblesdotdev.ohmylist.navigation.NavDestination
import com.roblesdotdev.ohmylist.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OhMyListTheme {
                NavGraph(
                    navController = rememberNavController(),
                    startDestination = NavDestination.ShopList,
                )
            }
        }
    }
}
