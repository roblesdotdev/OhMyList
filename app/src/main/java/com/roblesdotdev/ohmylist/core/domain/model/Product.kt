package com.roblesdotdev.ohmylist.core.domain.model

data class Product(
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val isChecked: Boolean = false,
) {
    val isValid = name.isNotBlank() && description.isNotBlank()
}
