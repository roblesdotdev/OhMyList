package com.roblesdotdev.ohmylist.core.data.local.mapper

import com.roblesdotdev.ohmylist.core.data.local.entity.ProductEntity
import com.roblesdotdev.ohmylist.core.data.local.entity.ShopListEntity
import com.roblesdotdev.ohmylist.core.data.local.entity.ShopListWithProducts
import com.roblesdotdev.ohmylist.core.domain.model.Product
import com.roblesdotdev.ohmylist.core.domain.model.ShopList

fun ShopListEntity.toDomain(): ShopList {
    return ShopList(
        id = this.id,
        title = this.title,
        description = this.description,
        group = this.group,
    )
}

fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        description = this.description,
        isChecked = this.isChecked,
    )
}

fun ShopListWithProducts.toDomain(): ShopList {
    return ShopList(
        id = this.shopList.id,
        description = this.shopList.description,
        title = this.shopList.title,
        group = this.shopList.group,
        products = this.products.map { it.toDomain() },
    )
}

fun Product.toEntity(listId: Int): ProductEntity {
    return ProductEntity(
        id = this.id,
        listId = listId,
        description = this.description,
        name = this.name,
        isChecked = this.isChecked,
    )
}

fun ShopList.toEntity(): ShopListEntity {
    return ShopListEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        group = this.group,
    )
}
