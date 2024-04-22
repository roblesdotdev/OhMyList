package com.roblesdotdev.ohmylist.shoplist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblesdotdev.ohmylist.shoplist.domain.model.ShopList
import com.roblesdotdev.ohmylist.shoplist.domain.repository.ShopListRepository
import com.roblesdotdev.ohmylist.util.AsyncResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ShopListViewModel(
    repo: ShopListRepository,
) : ViewModel() {
    private val isLoading = MutableStateFlow(false)
    private val filteredAsyncResult =
        repo.getShopListStream()
            .map {
                AsyncResult.Success(it)
            }.catch<AsyncResult<List<ShopList>>> { emit(AsyncResult.Error("Something went wrong")) }
    val state: StateFlow<ShopListState> =
        combine(isLoading, filteredAsyncResult) { isLoading, resultAsync ->
            when (resultAsync) {
                is AsyncResult.Error ->
                    ShopListState(
                        isLoading = false,
                        errorMessage = resultAsync.errorMessage,
                    )

                AsyncResult.Loading -> ShopListState(isLoading = true)
                is AsyncResult.Success ->
                    ShopListState(
                        items = resultAsync.data,
                        isLoading = isLoading,
                    )
            }
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ShopListState())
}
