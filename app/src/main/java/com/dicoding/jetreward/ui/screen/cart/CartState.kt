package com.dicoding.jetreward.ui.screen.cart

import com.dicoding.jetreward.model.OrderBook

data class CartState(
    val orderBook: List<OrderBook>,
    val totalRequiredPoint: Int
)