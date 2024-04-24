package com.roblesdotdev.ohmylist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.roblesdotdev.ohmylist.addShoplist.ui.AddShopListScreen
import com.roblesdotdev.ohmylist.addShoplist.ui.AddShopListViewModel
import com.roblesdotdev.ohmylist.core.ui.theme.OhMyListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AddShopListViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            OhMyListTheme {
                AddShopListScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onListSaved = {
                        Log.d("ADD", "List saved")
                    },
                )
            }
        }
    }
}
