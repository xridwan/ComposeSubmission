package com.eve.jetsubmission.ui.sreen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eve.jetsubmission.data.ProductRepository
import com.eve.jetsubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderProducts()
                .collect { orderProduct ->
                    val totalPrice =
                        orderProduct.sumOf { it.product.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderProduct, totalPrice))
                }
        }
    }

    fun updateOrderProduct(productId: Int, count: Int) {
        viewModelScope.launch {
            repository.updateOrderProduct(productId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderProducts()
                    }
                }
        }
    }
}