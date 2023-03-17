package com.rasyidin.moneyverse.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rasyidin.moneyverse.R

val MPlusRounded = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.m_plus_rounded_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.m_plus_rounded_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.m_plus_rounded_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.m_plus_rounded_thin,
            weight = FontWeight.Thin,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.m_plus_rounded_black,
            weight = FontWeight.Black,
            style = FontStyle.Normal
        )
    )
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Thin,
        fontSize = 14.sp
    ),
    h1 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Black,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Black,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h4 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    h6 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    button = TextStyle(
        fontFamily = MPlusRounded,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)