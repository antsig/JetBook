package com.dicoding.jetreward.di

import com.dicoding.jetreward.data.BookRepository


object Injection {
    fun provideRepository(): BookRepository {
        return BookRepository.getInstance()
    }
}