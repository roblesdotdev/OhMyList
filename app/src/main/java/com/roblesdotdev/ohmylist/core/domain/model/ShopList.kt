package com.roblesdotdev.ohmylist.core.domain.model

data class ShopList(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val group: String = "",
    var products: List<Product> = emptyList(),
)
