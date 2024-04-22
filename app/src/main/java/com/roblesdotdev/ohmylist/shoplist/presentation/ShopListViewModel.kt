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
    private val _isLoading = MutableStateFlow(false)
    private val _filter = MutableStateFlow("")
    private val _filteredAsyncResult =
        combine(repo.getShopListStream(), _filter) { list, filter ->
            filterList(list, filter)
        }.map {
            AsyncResult.Success(it)
        }.catch<AsyncResult<List<ShopList>>> { emit(AsyncResult.Error("Something went wrong")) }
    val state: StateFlow<ShopListState> =
        combine(_isLoading, _filter, _filteredAsyncResult) { isLoading, filter, resultAsync ->
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
                        filter = filter,
                    )
            }
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ShopListState())

    private fun filterList(
        list: List<ShopList>,
        filter: String?,
    ): List<ShopList> {
        if (filter?.isNotBlank() == true) {
            return list
        }
        return list
    }
}

data class ShopListState(
    val isLoading: Boolean = false,
    val items: List<ShopList> = emptyList(),
    val errorMessage: String? = null,
    val filter: String? = null,
)
