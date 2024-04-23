package com.roblesdotdev.ohmylist.core.domain.repository

import com.roblesdotdev.ohmylist.core.domain.model.Product
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import kotlinx.coroutines.flow.Flow

interface ShopListRepository {
    fun getShopListStream(): Flow<List<ShopList>>

    fun getShopListStreamById(id: Int): Flow<ShopList?>

    fun addProductToList(
        listId: Int,
        product: Product,
    )
}
