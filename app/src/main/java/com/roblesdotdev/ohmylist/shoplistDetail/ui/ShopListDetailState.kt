package com.roblesdotdev.ohmylist.shoplistDetail.ui

import com.roblesdotdev.ohmylist.core.domain.model.ShopList

data class ShopListDetailState(
    val isLoading: Boolean = false,
    val item: ShopList? = null,
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
    val input: ProductInput = ProductInput(),
)

data class ProductInput(
    val name: String = "",
    val description: String = "",
) {
    val isValid = name.isNotBlank() && description.isNotBlank()
}
