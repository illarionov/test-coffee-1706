package com.example.coffe1706.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

public object Coffee1706Typography {
    public val bodyLargeTextHuge: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp,
    )

    public val bodyLargeTextNormal: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    )

    public val bodyLargeTextBold: TextStyle = bodyLargeTextNormal.copy(fontWeight = FontWeight.Bold)

    public val bodyMediumVariant: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp,
    )

    public val supportingTextNormal: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp,
    )

    public val supportingTextBold: TextStyle = supportingTextNormal.copy(fontWeight = FontWeight.Bold)

    // Set of Material typography styles to start with
    public val materialTypography: Typography = Typography(
        bodyLarge = bodyLargeTextNormal,
        bodyMedium = supportingTextNormal,
        labelLarge = bodyLargeTextBold,
    )
}
