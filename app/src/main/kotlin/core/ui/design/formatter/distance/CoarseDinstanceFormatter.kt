package com.example.coffe1706.core.ui.design.formatter.distance

import android.content.res.Resources
import com.example.coffe1706.R
import java.text.DecimalFormat

/**
 * Форматтер расстояний в виде "1км от вас", "2 км от вас", "300 м от вас"…
 */
class CoarseDinstanceFormatter(
    private val resources: Resources,
) {
    private val decimalFormat: DecimalFormat = DecimalFormat("#.##")

    fun format(distance: CoarseDistance): String {
        return when (distance.unit) {
            CoarseDistance.DistanceUnit.METERS -> {
                val distanceMeters = distance.distance.toInt()
                resources.getQuantityString(R.plurals.meters_from_you, distanceMeters, distanceMeters)
            }
            CoarseDistance.DistanceUnit.KILOMETERS -> {
                val distanceKm = decimalFormat.format(distance.distance)
                val intSamples = distance.distance.toInt() // XXX wrong
                resources.getQuantityString(R.plurals.kilometers_from_you, intSamples, distanceKm)
            }
        }
    }
}
