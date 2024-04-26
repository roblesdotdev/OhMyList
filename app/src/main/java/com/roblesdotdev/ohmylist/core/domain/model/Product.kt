package com.roblesdotdev.ohmylist.core.domain.model

data class Product(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val isChecked: Boolean = false,
)

data class ProductInput(
    val name: String = "",
    val description: String = "",
    val isEdit: Boolean = false,
) {
    val isValid = name.isNotBlank() && description.isNotBlank()
}
