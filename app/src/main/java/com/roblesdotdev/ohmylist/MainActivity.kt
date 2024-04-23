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
import com.roblesdotdev.ohmylist.shoplistDetail.ui.ShopListDetailScreen
import com.roblesdotdev.ohmylist.shoplistDetail.ui.ShopListDetailViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel by viewModels<ShopListDetailViewModel>(
                factoryProducer = {
                    object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ShopListDetailViewModel(repo = DefaultShopListRepository()) as T
                        }
                    }
                },
            )
            val state by viewModel.state.collectAsState()
            OhMyListTheme {
                ShopListDetailScreen(state = state)
            }
        }
    }
}
