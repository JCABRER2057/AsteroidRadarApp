package com.udacity.asteroidradar.models

import com.squareup.moshi.Json
import com.udacity.asteroidradar.entities.PictureOfDayEntitity

data class PictureOfDay(
        val url: String,
        val title: String,
        @Json(name = "media_type") val mediaType: String)

fun PictureOfDay.asDatabaseModel() = PictureOfDayEntitity (
        url = url,
        title = title,
        mediaType = mediaType)
