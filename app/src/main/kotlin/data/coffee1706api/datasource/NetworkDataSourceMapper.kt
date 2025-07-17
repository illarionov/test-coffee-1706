package com.example.coffe1706.data.coffee1706api.datasource

import com.example.coffe1706.core.model.LatLon
import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.LocationMenuItem
import com.example.coffe1706.core.model.LocationMenuItemId
import com.example.coffe1706.core.model.auth.AuthToken
import com.example.coffe1706.core.model.auth.AuthTokenId
import com.example.coffe1706.data.coffee1706api.retrofit.service.LatLonDto
import com.example.coffe1706.data.coffee1706api.retrofit.service.LocationDto
import com.example.coffe1706.data.coffee1706api.retrofit.service.LocationMenuItemDto
import com.example.coffe1706.data.coffee1706api.retrofit.service.LoginResponseDto

internal fun LoginResponseDto.toAuthToken(): AuthToken = AuthToken(
    token = AuthTokenId(this.token),
    tokenLifeTime = this.tokenLifeTime,
)

internal fun LocationDto.toLocation(): Location = Location(
    id = LocationId(this.id),
    name = name,
    position = point.toLatLon(),
)

internal fun LatLonDto.toLatLon() = LatLon(
    latitude = latitude,
    longitude = longitude,
)

internal fun LocationMenuItemDto.toLocationMenuItem(): LocationMenuItem = LocationMenuItem(
    id = LocationMenuItemId(this.id),
    name = name,
    imageUrl = imageUrl,
    price = price,
)
