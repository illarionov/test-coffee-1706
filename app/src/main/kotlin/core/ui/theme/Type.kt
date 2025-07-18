package com.example.coffe1706.core.ui.theme3

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object Coffee1706Typography {
    val bodyLargeTextHuge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp,
    )

    val bodyLargeTextNormal = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    )

    val bodyLargeTextBold = bodyLargeTextNormal.copy(fontWeight = FontWeight.Bold)

    val bodyMediumVariant = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp,
    )

    val supportingTextNormal = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp,
    )

    val supportingTextBold = supportingTextNormal.copy(fontWeight = FontWeight.Bold)

    // Set of Material typography styles to start with
    val materialTypography = Typography(
        bodyLarge = bodyLargeTextNormal,
        bodyMedium = supportingTextNormal,
        labelLarge = bodyLargeTextBold,
    )
}

