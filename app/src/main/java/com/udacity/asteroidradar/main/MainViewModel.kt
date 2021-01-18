package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.AsteroidsApi

import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val _pictureOfDay  = MutableLiveData<PictureOfDay>()

    val pictureOfDay : LiveData<PictureOfDay>
        get() = _pictureOfDay
    /*** Call getPhotoofDay on Init*/
    init {
        getPhotooftheDay()
    }
    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getPhotooftheDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value= AsteroidsApi.retrofitService.getImageOfDay("jZdn6py9nG3lJHSnWNcyeHxig2zYvqsoX9ZcJ6Ik")

            } catch (e: Exception) {
            }
        }
    }
}