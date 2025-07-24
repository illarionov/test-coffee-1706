package com.example.coffe1706.core.ui.internationalization.formatter.distance

import com.example.coffe1706.core.ui.internationalization.formatter.distance.CoarseDistance.DistanceUnit.KILOMETERS
import com.example.coffe1706.core.ui.internationalization.formatter.distance.CoarseDistance.DistanceUnit.METERS
import java.math.BigDecimal
import java.math.RoundingMode.HALF_DOWN

/**
 * Приблизительное рассотояние для пользователя.
 *
 * Используем округленные значения, чтобы не рекомпозировать весь список при каждом
 * незначительном перемещении пользователя
 */
public class CoarseDistance private constructor(
    public val distance: BigDecimal,
    public val unit: DistanceUnit,
) {
    public enum class DistanceUnit {
        METERS,
        KILOMETERS,
    }

    public companion object {
        public operator fun invoke(distance: Int, unit: DistanceUnit): CoarseDistance =
            CoarseDistance(BigDecimal(distance), unit)

        public fun meters(distance: Int): CoarseDistance = CoarseDistance(distance, METERS)
        public fun kilometers(kilometers: Int): CoarseDistance = CoarseDistance(kilometers, KILOMETERS)
        public fun kilometers(kilometers: Double): CoarseDistance = CoarseDistance(
            BigDecimal(kilometers).setScale(1, HALF_DOWN),
            KILOMETERS
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CoarseDistance

        if (distance != other.distance) return false
        if (unit != other.unit) return false

        return true
    }

    override fun hashCode(): Int {
        var result = distance.hashCode()
        result = 31 * result + unit.hashCode()
        return result
    }

    override fun toString(): String {
        return "CoarseDistance(distance=$distance, unit=$unit)"
    }
}
