package com.roblesdotdev.ohmylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roblesdotdev.ohmylist.core.data.repository.DefaultShopListRepository
import com.roblesdotdev.ohmylist.core.ui.theme.OhMyListTheme
import com.roblesdotdev.ohmylist.shoplist.ui.ShopListScreen
import com.roblesdotdev.ohmylist.shoplist.ui.ShopListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel by viewModels<ShopListViewModel>(
                factoryProducer = {
                    object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ShopListViewModel(repo = DefaultShopListRepository()) as T
                        }
                    }
                },
            )
            val state by viewModel.state.collectAsState()
            OhMyListTheme {
                ShopListScreen(state = state)
            }
        }
    }
}
