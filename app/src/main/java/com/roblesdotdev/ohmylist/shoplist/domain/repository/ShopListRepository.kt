package com.roblesdotdev.ohmylist.shoplist.domain.repository

import com.roblesdotdev.ohmylist.shoplist.domain.model.ShopList
import kotlinx.coroutines.flow.Flow

interface ShopListRepository {
    fun getShopListStream(): Flow<List<ShopList>>
}
