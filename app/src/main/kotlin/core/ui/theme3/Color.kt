package com.example.coffe1706.core.ui.theme3

import androidx.compose.ui.graphics.Color

val Gray10 = Color(0xffFAF9F9)
val Gray30 = Color(0xffE5E5E5)
val Gray40 = Color(0xffC2C2C2)
val Gray70 = Color(0xff666666)

val Brown20 = Color(0xffF6E5D1)
val Brown30 = Color(0xffAF9479)
val Brown40 = Color(0xff846340)
val Brown40Dark = Color(0xff342D1A)

object Coffee1706Colors {
    /**
     * Цвет основного текста
     */
    val TextColor = Brown40

    /**
     * Цвет дополнительного текста, чуть светлее основного, placeholder'ы в EditText
     */
    val TextColorLighter = Brown30

    /**
     * Цвет дополнительного текста, ещё светлее основного
     */
    val TextColorLighterLighter = Brown20

    /**
     * Основной цвет на темных кнопках
     */
    val TextColorOnPrimary = TextColorLighterLighter

    /**
     * Цвет кнопок
     */
    val ColorPrimary = Brown40Dark

    /**
     * Основной фон и поверхности
     */
    val Background = Color.White

    /**
     * Цвет APP Bar
     */
    val SurfaceContainer = Gray10
}
