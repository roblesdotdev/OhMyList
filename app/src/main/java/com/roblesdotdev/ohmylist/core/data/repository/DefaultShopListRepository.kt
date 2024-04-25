package com.roblesdotdev.ohmylist.core.data.repository

import com.roblesdotdev.ohmylist.core.data.local.LocalDao
import com.roblesdotdev.ohmylist.core.data.local.mapper.toDomain
import com.roblesdotdev.ohmylist.core.data.local.mapper.toEntity
import com.roblesdotdev.ohmylist.core.domain.model.Product
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.domain.repository.ShopListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultShopListRepository
    @Inject
    constructor(
        private val localStore: LocalDao,
    ) : ShopListRepository {
        override fun getShopListStream(): Flow<List<ShopList>> {
            return localStore.getShopList().map { list ->
                list.map { it.toDomain() }
            }
        }

        override fun getShopListStreamById(id: Int): Flow<ShopList?> {
            return localStore.getShopListWithProducts(id).map { it.toDomain() }
        }

        override suspend fun upsertProductToList(
            listId: Int,
            product: Product,
        ) {
            withContext(Dispatchers.IO) {
                val productEntity = product.toEntity(listId)
                localStore.upsertShopListProduct(productEntity)
            }
        }

        override suspend fun upsertShopList(shopList: ShopList): Int {
            return withContext(Dispatchers.IO) {
                localStore.upsertShopList(shopList.toEntity()).toInt()
            }
        }
    }
