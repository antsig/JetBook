package com.dicoding.jetreward.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetreward.data.BookRepository
import com.dicoding.jetreward.model.Book
import com.dicoding.jetreward.model.OrderBook
import com.dicoding.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailBookViewModel(
    private val repository: BookRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderBook>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderBook>>
        get() = _uiState

    fun getRewardById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderBookById(rewardId))
        }
    }

    fun addToCart(book: Book, count: Int) {
        viewModelScope.launch {
            repository.updateOrderBook(book.id, count)
        }
    }
}