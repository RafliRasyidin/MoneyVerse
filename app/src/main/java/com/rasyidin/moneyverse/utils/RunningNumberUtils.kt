package com.rasyidin.moneyverse.utils

import com.pixplicity.easyprefs.library.Prefs
import java.util.*

object RunningNumberUtils {

    private const val PREFIX_TRANSACTION_OUTCOME = "OUT"
    private const val PREFIX_TRANSACTION_INCOME = "INC"
    private const val PREFIX_TRANSACTION_TRANSFER = "TRF"
    private const val PREFIX_ANGGARAN = "BGT"

    private const val startSuffix = "00000"

    fun getIdOutcomeTransaction(): String {
        var lastRunningNumber = Prefs.getString(PrefKey.TRANSACTION_OUTCOME, startSuffix)
        lastRunningNumber = generateRunningNumber(lastRunningNumber)

        Prefs.putString(PrefKey.TRANSACTION_OUTCOME, lastRunningNumber)
        val uuid = UUID.randomUUID().toString().substring(0..5).uppercase()
        return "#$PREFIX_TRANSACTION_OUTCOME$uuid/$lastRunningNumber"
    }

    fun getIdIncomeTransaction(): String {
        var lastRunningNumber = Prefs.getString(PrefKey.TRANSACTION_INCOME, startSuffix)
        lastRunningNumber = generateRunningNumber(lastRunningNumber)

        Prefs.putString(PrefKey.TRANSACTION_INCOME, lastRunningNumber)
        val uuid = UUID.randomUUID().toString().substring(0..5).uppercase()
        return "#$PREFIX_TRANSACTION_INCOME$uuid/$lastRunningNumber"
    }

    fun getIdTransferTransaction(): String {
        var lastRunningNumber = Prefs.getString(PrefKey.TRANSACTION_TRANSFER, startSuffix)
        lastRunningNumber = generateRunningNumber(lastRunningNumber)

        Prefs.putString(PrefKey.TRANSACTION_TRANSFER, lastRunningNumber)
        val uuid = UUID.randomUUID().toString().substring(0..5).uppercase()
        return "#$PREFIX_TRANSACTION_TRANSFER$uuid/$lastRunningNumber"
    }

    fun getIdAnggaran(): String {
        var lastRunningNumber = Prefs.getString(PrefKey.ANGGARAN, startSuffix)
        lastRunningNumber = generateRunningNumber(lastRunningNumber)

        Prefs.putString(PrefKey.ANGGARAN, lastRunningNumber)
        val uuid = UUID.randomUUID().toString().substring(0..3).uppercase()
        return "#$PREFIX_ANGGARAN$uuid/$lastRunningNumber"
    }

    private fun generateRunningNumber(lastRunningNumber: String): String {
        val text = StringBuilder()
        for (i in (lastRunningNumber.length-1..lastRunningNumber.lastIndex).reversed()){
            val n = lastRunningNumber[i].code.toByte().toInt()
            var next = n.inc()
            when(n){
                57-> next = 65
                90-> next = 48
            }

            text.insert(0,next.toChar())

            if (lastRunningNumber.length > 1) {
                if (n == 90 && continueNumber(lastRunningNumber[lastRunningNumber.lastIndex - 1])) {
                    val removeLast = lastRunningNumber.substring(0, lastRunningNumber.length - 1)
                    val textComplete = generateRunningNumber(removeLast)
                    text.insert(0, textComplete)
                } else if (n == 90 && !continueNumber(lastRunningNumber[lastRunningNumber.lastIndex - 1])) {
                    val sLeft = StringBuilder(lastRunningNumber.substring(0, lastRunningNumber.length - 1))
                    val leftNumber = lastRunningNumber[i - 1].code.toByte().toInt()
                    var nextLeft = leftNumber.inc()
                    when (leftNumber) {
                        57 -> nextLeft = 65
                    }
                    sLeft.setCharAt(sLeft.lastIndex, nextLeft.toChar())
                    text.insert(0, sLeft.toString())
                } else if (n != 90) {
                    text.insert(0, lastRunningNumber.substring(0, lastRunningNumber.length - 1))
                }
            }
        }

        return text.toString()
    }

    private fun continueNumber(c: Char): Boolean {
        return when(c.code){
            90 -> true
            else -> false
        }
    }
}