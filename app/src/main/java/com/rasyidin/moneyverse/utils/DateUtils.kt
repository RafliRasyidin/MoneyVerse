package com.rasyidin.moneyverse.utils

import android.annotation.SuppressLint
import android.content.Context
import com.rasyidin.moneyverse.R
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
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
            "00:00:00"
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

    fun YearMonth.displayText(short: Boolean = false): String {
        return "${month.displayText(short = short)} $year"
    }

    fun Month.displayText(short: Boolean = true): String {
        val style = if (short) TextStyle.SHORT else TextStyle.FULL
        return getDisplayName(style, Locale.getDefault())
    }

    fun DayOfWeek.displayText(dayStyle: TextStyle = TextStyle.SHORT, uppercase: Boolean = false): String {
        return getDisplayName(dayStyle, Locale.getDefault()).let { value ->
            if (uppercase) value.uppercase() else value
        }
    }

    fun String.getRelativeDate(context: Context): String {
        val formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)
        val dateTime = LocalDateTime.parse(this, formatter)
        val now = LocalDateTime.now()
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        return when {
            dateTime.toLocalDate() == today -> {
                val duration = Duration.between(dateTime, now)
                when {
                    duration.toMinutes() < 1 -> context.getString(R.string.baru_saja)
                    duration.toHours() < 1 -> context.getString(R.string.count_menit_lalu, duration.toMinutes())
                    else -> context.getString(R.string.hari_ini)
                }
            }
            dateTime.toLocalDate() == yesterday -> context.getString(R.string.kemarin)
            else -> this.formatDate("dd MMM yyyy")
        }
    }
}