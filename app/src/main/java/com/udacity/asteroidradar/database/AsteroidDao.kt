package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.entities.AsteroidEntity
import com.udacity.asteroidradar.entities.PictureOfDayEntitity
import com.udacity.asteroidradar.models.PictureOfDay

@Dao
interface AsteroidDao
{
    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
    fun getWeekAsteroids(startDate: String, endDate: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid_table  WHERE closeApproachDate = :todayDate ORDER BY closeApproachDate ASC")
    fun getTodayAsteroids(todayDate: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid_table  ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(vararg asteroids:AsteroidEntity)

    @Query("SELECT * FROM picture_table")
    fun getPicture(): LiveData<PictureOfDayEntitity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPicture(picture: PictureOfDayEntitity)
}