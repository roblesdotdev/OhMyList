package com.roblesdotdev.ohmylist.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ShopListWithProducts(
    @Embedded val shopList: ShopListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "listId",
    )
    val products: List<ProductEntity>,
)
