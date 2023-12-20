package com.dicoding.jetreward.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetreward.data.BookRepository
import com.dicoding.jetreward.model.Book
import com.dicoding.jetreward.model.OrderBook
import com.dicoding.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _groupedBooks = MutableStateFlow(
        repository.searchBooks("")
            .sortedBy { it.title }
            .groupBy { it.title[0] }
    )
    val groupedBooks: StateFlow<Map<Char, List<Book>>> get() = _groupedBooks

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedBooks.value = repository.searchBooks(_query.value)
            .sortedBy { it.title }
            .groupBy { it.title[0] }
    }

    private val _uiState: MutableStateFlow<UiState<List<OrderBook>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderBook>>>
        get() = _uiState

    fun getAllBooks() {
        viewModelScope.launch {
            repository.getAllBooks()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderRewards ->
                    _uiState.value = UiState.Success(orderRewards)
                }
        }
    }
}