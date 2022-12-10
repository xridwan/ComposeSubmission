package com.eve.jetsubmission.utils

import android.content.Context
import android.widget.Toast
import java.text.NumberFormat
import java.util.*


fun convertToRupiah(total: Long): String {
    val invoiceString = total.toString() + ""
    val rupiahFormat = NumberFormat.getInstance(Locale.CANADA)
    return ("Rp. "
            + rupiahFormat.format(invoiceString.toDouble()).replace(",", "."))
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
