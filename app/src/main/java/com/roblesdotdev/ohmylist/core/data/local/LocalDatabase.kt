package com.roblesdotdev.ohmylist.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roblesdotdev.ohmylist.core.data.local.entity.ProductEntity
import com.roblesdotdev.ohmylist.core.data.local.entity.ShopListEntity

@Database(
    entities = [ShopListEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class LocalDatabase : RoomDatabase() {
    abstract val dao: LocalDao
}
