package com.dicoding.jetreward.model

data class Book(
    val id: Long,
    val image: Int,
    val title: String,
    val requiredPoint: Int,
    val description: String
)