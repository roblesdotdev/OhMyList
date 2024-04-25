package com.roblesdotdev.ohmylist.addShoplist.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.domain.repository.ShopListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddShopListViewModel
    @Inject
    constructor(
        private val repo: ShopListRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val listId = savedStateHandle.get<String?>("listId")?.toIntOrNull()
        private val _state = MutableStateFlow(AddShopListState())
        val state = _state.asStateFlow()

        init {
            if (listId != null) {
                loadList(listId = listId)
            }
        }

        fun onEvent(event: AddShopListEvent) {
            when (event) {
                is AddShopListEvent.ChangeGroup -> updateGroup(event.group)
                is AddShopListEvent.ChangeTitle -> updateTitle(event.title)
                AddShopListEvent.Save -> saveList()
            }
        }

        private fun updateTitle(title: String) {
            _state.update { it.copy(title = title) }
        }

        private fun updateGroup(group: String) {
            _state.update { it.copy(group = group) }
        }

        private fun saveList() {
            viewModelScope.launch {
                var list =
                    ShopList(
                        title = state.value.title,
                        group = state.value.group,
                    )
                state.value.item?.let {
                    list =
                        list.copy(
                            id = it.id,
                            products = it.products,
                        )
                }
                repo.upsertShopList(list).let { listId ->
                    _state.update { it.copy(savedListId = listId) }
                }
            }
        }

        private fun loadList(listId: Int) {
            viewModelScope.launch {
                repo.getShopListStreamById(listId).collectLatest { shopList ->
                    shopList?.let {
                        _state.update {
                            it.copy(
                                title = shopList.title,
                                group = shopList.group,
                                item = shopList,
                            )
                        }
                    }
                }
            }
        }
    }
