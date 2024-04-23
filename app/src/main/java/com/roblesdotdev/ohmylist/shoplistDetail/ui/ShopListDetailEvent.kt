package com.roblesdotdev.ohmylist.shoplistDetail.ui

sealed interface ShopListDetailEvent {
    data object CloseModal : ShopListDetailEvent
    data object OpenModal : ShopListDetailEvent
    data object AddProduct : ShopListDetailEvent

    data class ChangeInputName(val name: String) : ShopListDetailEvent

    data class ChangeInputDescription(val description: String) : ShopListDetailEvent
}
