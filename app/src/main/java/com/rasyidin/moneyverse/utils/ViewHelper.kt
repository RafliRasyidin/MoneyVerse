package com.rasyidin.moneyverse.utils

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.core.CalendarMonth
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.ColorShadow
import kotlinx.coroutines.flow.filterNotNull

fun Long.toCurrency(): String {
    return String.format("%,d", this)
}

fun String.formatValue(delimiter: Char): String {
    return this.chunked(3).joinToString(delimiter.toString())
}

fun Modifier.dropShadow(
    color: Color = ColorShadow,
    alpha: Float = .06F,
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

fun Modifier.clickable(
    enabled: Boolean = true,
    showRipple: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = if (showRipple) LocalIndication.current else null,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick
    )
}

fun String.highlight(textStyle: TextStyle, vararg words: String, color: Color): AnnotatedString {
    return buildAnnotatedString {
        val regex = Regex("\\b(${words.joinToString("|")})\\b", RegexOption.IGNORE_CASE)
        val matches = regex.findAll(this@highlight)
        var curIndex = 0
        matches.forEach { matchResult ->
            val range = matchResult.range
            val plainText = this@highlight.substring(curIndex, range.first)
            if (plainText.isNotEmpty()) {
                append(plainText)
            }
            val highlightedText = this@highlight.substring(range)
            withStyle(textStyle.toSpanStyle().copy(color = color)) {
                append(highlightedText)
            }
            curIndex = range.last + 1
        }
        if (curIndex < this@highlight.length) {
            append(this@highlight.substring(curIndex))
        }
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

@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewPortPercent: Float = 50F
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewPortPercent) }
            .filterNotNull()
            .collect { month ->
                visibleMonth.value = month
            }
    }
    return visibleMonth.value
}

fun String.fromHtml(): Spanned? {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
}

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewPortPercent: Float = 50F): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) null else {
        val viewPortSize = (viewportEndOffset + viewportStartOffset) * viewPortPercent / 100F
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewPortSize
            } else {
                itemInfo.size - itemInfo.offset >= viewPortSize
            }
        }?.month
    }
}

fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // we should wait for all new pointer events
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        this
    }