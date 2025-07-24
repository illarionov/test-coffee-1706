package com.example.coffe1706.core.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.coffe1706.core.ui.theme.Coffee1706Colors.Base.Brown20
import com.example.coffe1706.core.ui.theme.Coffee1706Colors.Base.Brown30
import com.example.coffe1706.core.ui.theme.Coffee1706Colors.Base.Brown40
import com.example.coffe1706.core.ui.theme.Coffee1706Colors.Base.Brown40Dark
import com.example.coffe1706.core.ui.theme.Coffee1706Colors.Base.Gray10

public object Coffee1706Colors {
    /**
     * Цвет основного текста
     */
    public val TextColor: Color = Brown40

    /**
     * Цвет дополнительного текста, чуть светлее основного, placeholder'ы в EditText
     */
    public val TextColorLighter: Color = Brown30

    /**
     * Цвет дополнительного текста, ещё светлее основного
     */
    public val TextColorLighterLighter: Color = Brown20

    /**
     * Основной цвет на темных кнопках
     */
    public val TextColorOnPrimary: Color = TextColorLighterLighter

    /**
     * Цвет кнопок
     */
    public val ColorPrimary: Color = Brown40Dark

    /**
     * Основной фон и поверхности
     */
    public val Background: Color = Color.White

    /**
     * Цвет APP Bar
     */
    public val SurfaceContainer: Color = Gray10

    /**
     * Цвет поверхности элементов в списках
     */
    public val SurfaceContainerSecondary: Color = Brown20

    public object Base {
        public val Gray10: Color = Color(0xffFAF9F9)
        public val Gray30: Color = Color(0xffE5E5E5)
        public val Gray40: Color = Color(0xffC2C2C2)
        public val Gray70: Color = Color(0xff666666)

        public val Brown20: Color = Color(0xffF6E5D1)
        public val Brown30: Color = Color(0xffAF9479)
        public val Brown40: Color = Color(0xff846340)
        public val Brown40Dark: Color = Color(0xff342D1A)
    }
}
