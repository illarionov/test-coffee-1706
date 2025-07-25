package com.example.coffe1706.core.ui.internationalization.formatter.distance

import android.content.res.Resources
import com.example.coffe1706.core.ui.R
import java.text.DecimalFormat

/**
 * Форматтер расстояний в виде "1км от вас", "2 км от вас", "300 м от вас"…
 */
public class CoarseDinstanceFormatter(
    private val resources: Resources,
) {
    private val decimalFormat: DecimalFormat = DecimalFormat("#.##")

    public fun format(distance: CoarseDistance): String {
        return when (distance.unit) {
            CoarseDistance.DistanceUnit.METERS -> {
                val distanceMeters = distance.distance.toInt()
                resources.getQuantityString(R.plurals.meters_from_you, distanceMeters, distanceMeters)
            }
            CoarseDistance.DistanceUnit.KILOMETERS -> {
                val distanceKm: String? = decimalFormat.format(distance.distance)
                val intSamples = distance.distance.toInt() // XXX wrong
                resources.getQuantityString(R.plurals.kilometers_from_you, intSamples, distanceKm)
            }
        }
    }
}
