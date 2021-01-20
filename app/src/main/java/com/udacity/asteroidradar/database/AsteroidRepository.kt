package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.api.AsteroidsApi
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates

import com.udacity.asteroidradar.api.parseStringToAsteroidList
import com.udacity.asteroidradar.entities.asDomainModel
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.models.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository (private val database: AsteroidDatabase){

    var arrdates= getNextSevenDaysFormattedDates()
    /*** A list of asteroids that can be show on the screen */
    val allAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAllAsteroids())
    {
        it.asDomainModel()
    }

    /*** A list of asteroids that can be show on the screen */
    val weekAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getWeekAsteroids(arrdates[0].toString(), arrdates[6].toString()))
    {
        it.asDomainModel()
    }

    /*** A list of asteroids that can be show on the screen */
    val todayAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getTodayAsteroids(arrdates[0].toString()))
    {
        it.asDomainModel()
    }

    /*******The photo of the day******/
    val picture: LiveData<PictureOfDay> = Transformations.map(database.asteroidDao.getPicture())
    {
        it.asDomainModel()
    }

    /**
     * Refresh the asteroids stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the videos for use, observe [Asteroids]
     */
    suspend fun refreshAsteroids()
    {
        withContext(Dispatchers.IO) {
            val listAsteroidModel = AsteroidsApi.retrofitService.getAsteroids(API_KEY)
            var asteroidArray=parseStringToAsteroidList(listAsteroidModel)
            database.asteroidDao.insertAllAsteroids(*asteroidArray.asDatabaseModel())

        }
    }
    suspend fun refreshPicture()
    {
        withContext(Dispatchers.IO){
            val pictureOfDayModel = AsteroidsApi.retrofitService.getPictureOfDay(API_KEY)
            val pictureOfDayEntitity = pictureOfDayModel.asDatabaseModel()

            database.asteroidDao.insertPicture( pictureOfDayEntitity)

        }
    }


}