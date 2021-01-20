package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.models.Asteroid

/*** RecyclerView Adapter for setting up data binding on the items in the list***/
class AsteroidAdapter (val callback: AsteroidClick) : RecyclerView.Adapter<AsteroidHolder>() {

    var asteroids : List<Asteroid> = emptyList()
    set(value) {
        field = value
        // For an extra challenge, update this to use the paging library.

        // Notify any registered observers that the data set has changed. This will cause every
        // element in our RecyclerView to be invalidated.
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidHolder {
        val withDataBinding: AsteroidItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                AsteroidHolder.LAYOUT,
                parent,
        false)
        return AsteroidHolder(withDataBinding)
    }



    override fun getItemCount() = asteroids.size

    override fun onBindViewHolder(holder: AsteroidHolder, position: Int) {
        holder.viewDataBinding.also {
            it.asteroid = asteroids[position]

        }
    }

}