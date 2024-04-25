package com.roblesdotdev.ohmylist.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopList")
data class ShopListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val group: String = "",
)
