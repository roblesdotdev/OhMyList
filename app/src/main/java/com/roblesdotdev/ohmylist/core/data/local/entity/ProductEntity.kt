package com.roblesdotdev.ohmylist.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String = "",
    val description: String = "",
    val isChecked: Boolean = false,
    val listId: Int,
)
