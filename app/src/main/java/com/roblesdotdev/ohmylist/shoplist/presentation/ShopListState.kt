package com.roblesdotdev.ohmylist.shoplist.presentation

import com.roblesdotdev.ohmylist.shoplist.domain.model.ShopList

data class ShopListState(
    val isLoading: Boolean = false,
    val items: List<ShopList> = emptyList(),
    val errorMessage: String? = null,
)
