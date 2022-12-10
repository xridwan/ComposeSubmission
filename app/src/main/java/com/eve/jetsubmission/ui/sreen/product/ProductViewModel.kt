package com.eve.jetsubmission.ui.sreen.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eve.jetsubmission.data.ProductRepository
import com.eve.jetsubmission.model.OrderProduct
import com.eve.jetsubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<OrderProduct>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderProduct>>>
        get() = _uiState

    fun getAllProduct() {
        viewModelScope.launch {
            repository.getAllProducts()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderProducts ->
                    _uiState.value = UiState.Success(orderProducts)
                }
        }
    }
}