package com.roblesdotdev.ohmylist.shoplistDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblesdotdev.ohmylist.core.domain.model.Product
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.domain.repository.ShopListRepository
import com.roblesdotdev.ohmylist.core.util.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShopListDetailViewModel
    @Inject
    constructor(
        private val repo: ShopListRepository,
    ) : ViewModel() {
        private val shopListId = 1

        private val isLoading = MutableStateFlow(false)
        private val showDialog = MutableStateFlow(false)
        private val currentProduct = MutableStateFlow(Product())
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
                currentProduct,
            ) { showDialog, isLoading, result, currentProduct ->
                when (result) {
                    is AsyncResult.Error ->
                        ShopListDetailState(
                            isLoading = false,
                            showDialog = showDialog,
                            errorMessage = result.errorMessage,
                            currentProduct = currentProduct,
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
                            currentProduct = currentProduct,
                        )
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                ShopListDetailState(isLoading = true),
            )

        fun onEvent(event: ShopListDetailEvent) {
            when (event) {
                ShopListDetailEvent.AddProduct -> saveListProduct()
                ShopListDetailEvent.CloseModal -> setShowDialog(false)
                ShopListDetailEvent.OpenModal -> setShowDialog(true)
                is ShopListDetailEvent.ChangeInputDescription -> updateInputDescription(event.description)
                is ShopListDetailEvent.ChangeInputName -> updateInputName(event.name)
                is ShopListDetailEvent.EditProduct -> openEditDialog(event.product)
                is ShopListDetailEvent.ToggleProductChecked -> toggleProductChecked(event.product)
            }
        }

        private fun toggleProductChecked(product: Product) {
            currentProduct.value = product
            repo.upsertProductToList(
                listId = shopListId,
                product = currentProduct.value.copy(isChecked = !currentProduct.value.isChecked),
            )
            currentProduct.value = Product()
        }

        private fun openEditDialog(product: Product) {
            currentProduct.value =
                currentProduct.value.copy(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    isChecked = product.isChecked,
                )
            setShowDialog(true)
        }

        private fun updateInputName(name: String) {
            currentProduct.value = currentProduct.value.copy(name = name)
        }

        private fun updateInputDescription(description: String) {
            currentProduct.update { it.copy(description = description) }
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
                currentProduct.value = Product()
            }
        }

        private fun saveListProduct() {
            repo.upsertProductToList(listId = shopListId, product = currentProduct.value)
            setShowDialog(false)
        }
    }
