package com.roblesdotdev.ohmylist.shoplistDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.domain.repository.ShopListRepository
import com.roblesdotdev.ohmylist.core.util.AsyncResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ShopListDetailState(
    val isLoading: Boolean = false,
    val item: ShopList? = null,
    val errorMessage: String? = null,
)

class ShopListDetailViewModel(
    repo: ShopListRepository,
) : ViewModel() {
    private val shopListId = 1

    private val isLoading = MutableStateFlow(false)
    private val asyncResult =
        repo.getShopListStreamById(shopListId)
            .map { shopList ->
                handleAsyncResult(shopList)
            }
            .catch { emit(AsyncResult.Error("Something went wrong loading list")) }
    val state =
        combine(isLoading, asyncResult) { isLoading, result ->
            when (result) {
                is AsyncResult.Error ->
                    ShopListDetailState(
                        isLoading = false,
                        errorMessage = result.errorMessage,
                    )

                AsyncResult.Loading -> ShopListDetailState(isLoading = true)
                is AsyncResult.Success -> ShopListDetailState(isLoading = isLoading, item = result.data)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ShopListDetailState(isLoading = true),
        )

    private fun handleAsyncResult(shopList: ShopList?): AsyncResult<ShopList?> {
        if (shopList == null) {
            return AsyncResult.Error("Invalid shop list id")
        }
        return AsyncResult.Success(shopList)
    }
}
