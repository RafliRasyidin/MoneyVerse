package com.rasyidin.moneyverse.utils

import android.util.Log

val Any?.isNull get() = this == null

val Any?.isNotNull get() = this != null

fun Any?.ifNull(block: () -> Unit) = run {
    if (this == null) {
        block()
    }
}

fun Any?.ifNotNull(block: () -> Unit) = run {
    if (this != null) {
        block()
    }
}

fun Any?.printToLog(tag: String = "DEBUG_LOG") {
    Log.d(tag, toString())
}