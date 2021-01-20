package com.udacity.asteroidradar.entities

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.models.PictureOfDay

@Entity(tableName = "picture_table")
data class PictureOfDayEntitity constructor(
    @PrimaryKey
    val url: String,
    val title: String,
    val mediaType: String
    )


fun PictureOfDayEntitity.asDomainModel() = PictureOfDay (
        url = url,
        title = title,
        mediaType = mediaType)


