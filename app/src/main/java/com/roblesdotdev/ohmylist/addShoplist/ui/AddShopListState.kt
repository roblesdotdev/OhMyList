package com.roblesdotdev.ohmylist.addShoplist.ui

import com.roblesdotdev.ohmylist.core.domain.model.ShopList

data class AddShopListState(
    val savedListId: Int? = null,
    val title: String = "",
    val group: String = "General",
    val item: ShopList? = null,
) {
    val isFormValid = title.isNotBlank() && group.isNotBlank()
}
