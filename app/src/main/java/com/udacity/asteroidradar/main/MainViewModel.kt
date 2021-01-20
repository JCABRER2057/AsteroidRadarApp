package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.AsteroidsApi
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.database.AsteroidRepository

import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.launch


class MainViewModel (application: Application) : AndroidViewModel(application) {
    enum class FilterAsteroid {
        TODAY,
        WEEK,
        ALL
    }
    private val database = getInstance(application)
    private val asteroidRepository = AsteroidRepository(database)

    var pictureOfDay = asteroidRepository.picture

    /**********Filter**********************/
    private var _filterAsteroid = MutableLiveData(FilterAsteroid.ALL)

    /*********Every time filterAsteroid changes********/
    val asteroids = Transformations.switchMap(_filterAsteroid) {
        when (it!!) {
            FilterAsteroid.WEEK -> asteroidRepository.weekAsteroids
            FilterAsteroid.TODAY -> asteroidRepository.todayAsteroids
            else -> asteroidRepository.allAsteroids
        }
    }

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
            asteroidRepository.refreshPicture()
        }
    }
    fun onChangeFilter(filter: FilterAsteroid) {
        _filterAsteroid.postValue(filter)
    }

    //var asteroids = videosRepository.asteroids
}