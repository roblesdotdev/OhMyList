package com.roblesdotdev.ohmylist.shoplistDetail.ui

import com.roblesdotdev.ohmylist.core.domain.model.Product

sealed interface ShopListDetailEvent {
    data object CloseModal : ShopListDetailEvent
    data object OpenModal : ShopListDetailEvent
    data object AddProduct : ShopListDetailEvent

    data class ChangeInputName(val name: String) : ShopListDetailEvent

    data class ChangeInputDescription(val description: String) : ShopListDetailEvent

    data class EditProduct(val product: Product) : ShopListDetailEvent

    data class ToggleProductChecked(val product: Product) : ShopListDetailEvent
}
