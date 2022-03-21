package com.dhatuker.zwallet.util

import android.widget.TextView
import java.text.DecimalFormat

object Helper {
    fun TextView.formatPrice(value: String){
        this.text = formatIDR(java.lang.Double.parseDouble(value))
    }

    fun formatIDR(price: Double?): String {
        val format = DecimalFormat("#,###,###")
        return "Rp" + format.format(price).replace(",".toRegex(),".")
    }
}