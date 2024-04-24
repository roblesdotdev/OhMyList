package com.roblesdotdev.ohmylist.addShoplist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roblesdotdev.ohmylist.core.domain.repository.ShopListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddShopListViewModel
    @Inject
    constructor(
        private val repo: ShopListRepository,
    ) : ViewModel() {
        private val _state = MutableStateFlow(AddShopListState())
        val state = _state.asStateFlow()

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
                repo.saveShopList(
                    title = _state.value.title,
                    group = _state.value.group,
                )
                _state.update { it.copy(isSaved = true) }
            }
        }
    }
