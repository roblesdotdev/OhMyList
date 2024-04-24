package com.roblesdotdev.ohmylist.addShoplist.ui

sealed interface AddShopListEvent {
    data class ChangeTitle(val title: String) : AddShopListEvent

    data class ChangeGroup(val group: String) : AddShopListEvent
    data object Save : AddShopListEvent
}
