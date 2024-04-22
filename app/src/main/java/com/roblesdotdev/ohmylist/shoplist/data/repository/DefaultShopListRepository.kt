package com.roblesdotdev.ohmylist.shoplist.data.repository

import com.roblesdotdev.ohmylist.shoplist.domain.model.ShopList
import com.roblesdotdev.ohmylist.shoplist.domain.repository.ShopListRepository
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
            )
        }

    override fun getShopListStream(): Flow<List<ShopList>> {
        return flowOf(items)
    }

    companion object {
        const val MIN = 1
        const val MAX = 5
    }
}
