package com.roblesdotdev.ohmylist.addShoplist.ui

data class AddShopListState(
    val listId: Int? = null,
    val title: String = "",
    val group: String = "General",
    val isSaved: Boolean = false,
) {
    val isFormValid = title.isNotBlank() && group.isNotBlank()
}
