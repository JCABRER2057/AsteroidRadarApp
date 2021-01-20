package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.AsteroidsApi
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.database.AsteroidRepository

import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.launch


class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val database = getInstance(application)
    private val videosRepository = AsteroidRepository(database)
    init {
        viewModelScope.launch {
            videosRepository.refreshAsteroids()
            videosRepository.refreshPicture()
        }
    }
}