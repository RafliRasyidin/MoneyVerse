package com.rasyidin.moneyverse.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val NORMAL_DATE_FORMAT = "d MMMM yyyy"
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DEFAULT_TIME_FORMAT = "HH:mm:ss"

    fun getCurrentDate(pattern: String = DEFAULT_DATE_TIME_FORMAT): String {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale("IND", "ID"))
            sdf.format(Date())
        } catch (e: Exception) {
            ""
        }
    }

    fun getCurrentTime(pattern: String = DEFAULT_TIME_FORMAT): String {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale("IND", "ID"))
            sdf.format(Date().time)
        } catch (e: Exception) {
            "Time not found"
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun String.formatDate(from: String= DEFAULT_DATE_TIME_FORMAT, to: String = NORMAL_DATE_FORMAT): String {
        return try {
            val local = SimpleDateFormat(from)
            val formater = SimpleDateFormat(to)
            formater.format(local.parse(this))
        } catch (e: Exception) {
            e.printStackTrace()
            this
        }
    }
}