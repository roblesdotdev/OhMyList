package com.roblesdotdev.ohmylist.shoplistDetail.ui

import com.roblesdotdev.ohmylist.core.domain.model.ProductInput
import com.roblesdotdev.ohmylist.core.domain.model.ShopList

data class ShopListDetailState(
    val isLoading: Boolean = false,
    val item: ShopList? = null,
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
    val productInput: ProductInput = ProductInput(),
)
