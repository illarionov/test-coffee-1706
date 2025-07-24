package com.example.coffe1706.feature.nearestcoffeeshops

import android.app.Activity
import com.yandex.mapkit.MapKitFactory

public object Initializer {
    public fun initYandexMapkit(
        key: String
    ) {
        MapKitFactory.setApiKey(key)
    }

    public fun initializeYandexMapkitActivity(activity: Activity) {
        MapKitFactory.initialize(activity)
    }
}

