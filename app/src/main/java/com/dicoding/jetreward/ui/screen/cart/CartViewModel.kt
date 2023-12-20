package com.dicoding.jetreward.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetreward.data.BookRepository
import com.dicoding.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: BookRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderBooks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderBooks()
                .collect { orderBook ->
                    val totalRequiredPoint =
                        orderBook.sumOf { it.book.requiredPoint * it.count }
                    _uiState.value = UiState.Success(CartState(orderBook, totalRequiredPoint))
                }
        }
    }

    fun updateOrderBook(rewardId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderBook(rewardId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderBooks()
                    }
                }
        }
    }
}