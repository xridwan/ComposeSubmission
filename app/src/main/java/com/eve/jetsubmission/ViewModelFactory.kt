package com.eve.jetsubmission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eve.jetsubmission.data.ProductRepository
import com.eve.jetsubmission.ui.sreen.cart.CartViewModel
import com.eve.jetsubmission.ui.sreen.detail.DetailViewModel
import com.eve.jetsubmission.ui.sreen.product.ProductViewModel

class ViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}