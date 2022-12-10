package com.eve.jetsubmission.di

import com.eve.jetsubmission.data.ProductRepository

object Injection {
    fun provideRepository(): ProductRepository {
        return ProductRepository.getInstance()
    }
}