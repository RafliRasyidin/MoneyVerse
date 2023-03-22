package com.rasyidin.moneyverse.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.ColorShadow

fun Long.toCurrency(): String {
    return String.format("%,d", this)
}

fun String.formatValue(delimiter: Char): String {
    return this.chunked(3).joinToString(delimiter.toString())
}

fun Modifier.dropShadow(
    color: Color = ColorShadow,
    alpha: Float = .1F,
    cornersRadius: Dp = 8.dp,
    shadowBlurRadius: Dp = 16.dp,
    offsetY: Dp = 3.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showShortToast(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showLongToast(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
}

fun Context.hideBotNav() {
    val botNav =
        (this as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bot_nav)
    val fabAdd = this.findViewById<FloatingActionButton>(R.id.fab_add)
    val fabBackground = this.findViewById<View>(R.id.fab_bg)
    botNav.visibility = View.GONE
    fabAdd.visibility = View.GONE
    fabBackground.visibility = View.GONE
}

fun Fragment.hideBotNav() {
    val botNav =
        (requireActivity() as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bot_nav)
    val fabAdd = requireActivity().findViewById<FloatingActionButton>(R.id.fab_add)
    val fabBackground = requireActivity().findViewById<View>(R.id.fab_bg)
    botNav.visibility = View.GONE
    fabAdd.visibility = View.GONE
    fabBackground.visibility = View.GONE
}

fun Fragment.showBotNav() {
    val botNav =
        (requireActivity() as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bot_nav)
    val fabAdd = requireActivity().findViewById<FloatingActionButton>(R.id.fab_add)
    val fabBackground = requireActivity().findViewById<View>(R.id.fab_bg)
    botNav.visibility = View.VISIBLE
    fabAdd.visibility = View.VISIBLE
    fabBackground.visibility = View.VISIBLE
}