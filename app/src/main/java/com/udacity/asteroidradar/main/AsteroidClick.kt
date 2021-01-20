package com.udacity.asteroidradar.main

import com.udacity.asteroidradar.models.Asteroid

class AsteroidClick (val block: (Asteroid) -> Unit) {
    /**
     * Called when a video is clicked
     *
     * @param asteroid the video that was clicked
     */
    fun onClick(asteroid: Asteroid) = block(asteroid)

}