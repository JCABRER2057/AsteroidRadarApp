package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.bindImage
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.main.MainViewModel.FilterAsteroid.*
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var viewModelAdapter: AsteroidAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.asteroids.observe(viewLifecycleOwner, Observer<List<Asteroid>>
        { asteroids ->
            asteroids.apply {
                viewModelAdapter?.asteroids = asteroids
            }
        })


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        /***************Click and navigate to asteroid detail********/
        viewModelAdapter = AsteroidAdapter(AsteroidClick {
            viewModel.displayAsteroidDetail(it)
        })

        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailComplete()
            }
        })

        binding.root.findViewById<RecyclerView>(R.id.asteroid_recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        /*****Change the filter in the ModelView*******/
        viewModel.onChangeFilter(
                when (item.itemId) {
                    R.id.show_rent_menu -> {
                        MainViewModel.FilterAsteroid.TODAY
                    }
                    R.id.show_all_menu -> {
                        MainViewModel.FilterAsteroid.WEEK
                    }
                    else -> {
                        MainViewModel.FilterAsteroid.ALL
                    }

                }
        )

        return true
    }
}

