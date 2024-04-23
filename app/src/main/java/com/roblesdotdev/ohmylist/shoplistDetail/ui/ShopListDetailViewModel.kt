package com.roblesdotdev.ohmylist.shoplistDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblesdotdev.ohmylist.core.domain.model.Product
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.domain.repository.ShopListRepository
import com.roblesdotdev.ohmylist.core.util.AsyncResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ShopListDetailViewModel(
    private val repo: ShopListRepository,
) : ViewModel() {
    private val shopListId = 1

    private val isLoading = MutableStateFlow(false)
    private val showDialog = MutableStateFlow(false)
    private val productInput = MutableStateFlow(ProductInput())
    private val asyncResult =
        repo.getShopListStreamById(shopListId)
            .map { shopList ->
                handleAsyncResult(shopList)
            }
            .catch { emit(AsyncResult.Error("Something went wrong loading list")) }
    val state =
        combine(
            showDialog,
            isLoading,
            asyncResult,
            productInput,
        ) { showDialog, isLoading, result, productInput ->
            when (result) {
                is AsyncResult.Error ->
                    ShopListDetailState(
                        isLoading = false,
                        showDialog = showDialog,
                        errorMessage = result.errorMessage,
                        input = productInput,
                    )

                AsyncResult.Loading ->
                    ShopListDetailState(
                        isLoading = true,
                        showDialog = showDialog,
                    )

                is AsyncResult.Success ->
                    ShopListDetailState(
                        isLoading = isLoading,
                        item = result.data,
                        showDialog = showDialog,
                        input = productInput,
                    )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ShopListDetailState(isLoading = true),
        )

    fun onEvent(event: ShopListDetailEvent) {
        when (event) {
            ShopListDetailEvent.AddProduct -> addProductToList()
            ShopListDetailEvent.CloseModal -> setShowDialog(false)
            ShopListDetailEvent.OpenModal -> setShowDialog(true)
            is ShopListDetailEvent.ChangeInputDescription -> updateInputDescription(event.description)
            is ShopListDetailEvent.ChangeInputName -> updateInputName(event.name)
        }
    }

    private fun updateInputName(name: String) {
        productInput.value = productInput.value.copy(name = name)
    }

    private fun updateInputDescription(description: String) {
        productInput.update { it.copy(description = description) }
    }

    private fun handleAsyncResult(shopList: ShopList?): AsyncResult<ShopList?> {
        if (shopList == null) {
            return AsyncResult.Error("Invalid shop list id")
        }
        return AsyncResult.Success(shopList)
    }

    private fun setShowDialog(value: Boolean) {
        showDialog.value = value
        if (!value) {
            productInput.value = ProductInput()
        }
    }

    private fun addProductToList() {
        val product =
            Product(name = productInput.value.name, description = productInput.value.description)
        repo.addProductToList(shopListId, product)
        setShowDialog(false)
    }
}
