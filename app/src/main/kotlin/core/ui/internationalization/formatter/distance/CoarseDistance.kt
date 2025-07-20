package com.example.coffe1706.core.ui.internationalization.formatter.distance

import java.math.BigDecimal

/**
 * Приблизительное рассотояние для пользователя.
 *
 * Используем округленные значения, чтобы не рекомпозировать весь список при каждом
 * незначительном перемещении пользователя
 */
public class CoarseDistance private constructor(
    val distance: BigDecimal,
    val unit: DistanceUnit,
) {
    enum class DistanceUnit {
        METERS,
        KILOMETERS,
    }

    companion object {
        operator fun invoke(distance: Int, unit: DistanceUnit): CoarseDistance =
            CoarseDistance(BigDecimal(distance), unit)

        fun meters(distance: Int) = CoarseDistance(distance, DistanceUnit.METERS)
        fun kilometers(kilometers: Int) = CoarseDistance(kilometers, DistanceUnit.KILOMETERS)
        fun kilometers(kilometers: Double) = CoarseDistance(
            BigDecimal(kilometers).setScale(1),
            DistanceUnit.KILOMETERS
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
