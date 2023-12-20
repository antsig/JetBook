package com.dicoding.jetreward.data

import com.dicoding.jetreward.model.Book
import com.dicoding.jetreward.model.FakeBookDataSource
import com.dicoding.jetreward.model.OrderBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class BookRepository {

    private val orderBooks = mutableListOf<OrderBook>()

    init {
        if (orderBooks.isEmpty()) {
            FakeBookDataSource.dummyBooks.forEach {
                orderBooks.add(OrderBook(it, 0))
            }
        }
    }

    fun getAllBooks(): Flow<List<OrderBook>> {
        return flowOf(orderBooks)
    }

    fun getOrderBookById(foodId: Long): OrderBook {
        return orderBooks.first {
            it.book.id == foodId
        }
    }

    fun updateOrderBook(foodId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderBooks.indexOfFirst { it.book.id == foodId }
        val result = if (index >= 0) {
            val orderBook = orderBooks[index]
            orderBooks[index] =
                orderBook.copy(book = orderBook.book, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderBooks(): Flow<List<OrderBook>> {
        return getAllBooks()
            .map { orderBooks ->
                orderBooks.filter { orderBook ->
                    orderBook.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(): BookRepository =
            instance ?: synchronized(this) {
                BookRepository().apply {
                    instance = this
                }
            }
    }

    fun searchBooks(query: String): List<Book> {
        return FakeBookDataSource.dummyBooks.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }
}