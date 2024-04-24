package com.roblesdotdev.ohmylist.core.di

import com.roblesdotdev.ohmylist.core.data.repository.DefaultShopListRepository
import com.roblesdotdev.ohmylist.core.domain.repository.ShopListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShopListModule {
    @Singleton
    @Provides
    fun providesShopListRepository(): ShopListRepository {
        return DefaultShopListRepository()
    }
}
