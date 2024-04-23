package com.roblesdotdev.ohmylist.core.data.repository

import com.roblesdotdev.ohmylist.core.domain.model.Product
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.domain.repository.ShopListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DefaultShopListRepository : ShopListRepository {
    private val items =
        (MIN..MAX).map {
            ShopList(
                id = it,
                title = "Shop list item #$it",
                description = "Shop list item description for #$it",
                group = "General",
                products =
                    listOf(
                        Product(id = 1, name = "Bread", description = "500gr", isChecked = true),
                        Product(id = 2, name = "Pizza", description = "2u", isChecked = false),
                        Product(id = 3, name = "Apple", description = "1kg", isChecked = false),
                    ),
            )
        }

    override fun getShopListStream(): Flow<List<ShopList>> {
        return flowOf(items)
    }

    override fun getShopListStreamById(id: Int): Flow<ShopList?> {
        val result = items.firstOrNull { it.id == id }
        return flowOf(result)
    }

    companion object {
        const val MIN = 1
        const val MAX = 5
    }
}
