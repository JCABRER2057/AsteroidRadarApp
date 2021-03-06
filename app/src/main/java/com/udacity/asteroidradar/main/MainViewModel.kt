package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidsApi
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.models.Asteroid

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
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    /**********Filter**********************/
    private var _filterAsteroid = MutableLiveData(FilterAsteroid.ALL)
    /*********Selected asteroid**********/
    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: MutableLiveData<Asteroid>
            get() = _navigateToSelectedAsteroid

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
            try {
                asteroidRepository.refreshAsteroids()
            }catch(exc:Exception){
                Log.e("AsteroidViewModel",exc.message,exc)
            }

            try{
                asteroidRepository.refreshPicture()
                _pictureOfDay.value=AsteroidsApi.retrofitService.getPictureOfDay(Constants.API_KEY)
            }catch(exc:Exception){
                Log.e("AsteroidViewModel",exc.message,exc)
            }
        }
    }

    fun displayAsteroidDetail(asteroid: Asteroid){
        _navigateToSelectedAsteroid.value = asteroid
    }
    fun displayAsteroidDetailComplete(){
        _navigateToSelectedAsteroid.value = null
    }
    fun onChangeFilter(filter: FilterAsteroid) {
        _filterAsteroid.postValue(filter)
    }



}