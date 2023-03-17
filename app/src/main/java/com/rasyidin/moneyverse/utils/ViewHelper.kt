package com.rasyidin.moneyverse.utils

fun Long.toCurrency(): String {
    return String.format("%,d", this)
}

fun String.formatValue(delimiter: Char): String {
    return this.chunked(3).joinToString(delimiter.toString())
}