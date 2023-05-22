package com.eve.jetsubmission.ui.screen.cart

import com.eve.jetsubmission.model.OrderProduct

data class CartState(
    val orderProduct: List<OrderProduct>,
    val totalPrice: Long
)