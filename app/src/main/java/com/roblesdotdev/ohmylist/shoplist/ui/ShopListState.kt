package com.roblesdotdev.ohmylist.shoplist.ui

import com.roblesdotdev.ohmylist.core.domain.model.ShopList

data class ShopListState(
    val isLoading: Boolean = false,
    val items: List<ShopList> = emptyList(),
    val errorMessage: String? = null,
)
