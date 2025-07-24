package com.example.coffe1706.core.model.fixtures

import com.example.coffe1706.core.model.LatLon
import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.model.LocationId

public object LocationFixtures {
    public val bedoefCoffee1: Location = Location(LocationId("1"), "BEDOEV COFFEE", LatLon(44.71415202330153, 37.78375413756531))
    public val coffeeLike: Location = Location(LocationId("2"), "Coffee Like", LatLon(44.69428368665135, 37.787392336364924))
    public val emdi: Location = Location(
        LocationId("3"),
        "EM&DI Coffee and Snacks",
        LatLon(44.71746117979872, 37.778758868087984),
    )
    public val coffeEst: Location = Location(LocationId("4"), "Коффе есть", LatLon(44.72457541012046, 37.774416523909665))
    public val bedoefCoffee2: Location = Location(LocationId("5"), "BEDOEV COFFEE 2", LatLon(44.686027192025946, 37.78135087835703))
}
