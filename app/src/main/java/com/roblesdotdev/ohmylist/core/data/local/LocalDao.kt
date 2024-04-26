package com.roblesdotdev.ohmylist.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.roblesdotdev.ohmylist.core.data.local.entity.ProductEntity
import com.roblesdotdev.ohmylist.core.data.local.entity.ShopListEntity
import com.roblesdotdev.ohmylist.core.data.local.entity.ShopListWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertShopList(shopList: ShopListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertShopListProduct(product: ProductEntity)

    @Transaction
    @Query("SELECT * FROM shopList WHERE id = :shopListId")
    fun getShopListWithProducts(shopListId: Int): Flow<ShopListWithProducts>

    @Query("SELECT * from shopList")
    fun getShopList(): Flow<List<ShopListEntity>>
}
