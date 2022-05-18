package com.mendelin.tmdb_hilt.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.mendelin.tmdb_hilt.R
import com.mendelin.tmdb_hilt.databinding.ActivityTmdbBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TmdbActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTmdbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = ActivityTmdbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setHomeButtonEnabled(true)
        }

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        binding.navBottom.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            return@OnItemSelectedListener when (item.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.nav_movies -> {
                    navController.navigate(R.id.moviesFragment)
                    true
                }
                R.id.nav_tv -> {
                    navController.navigate(R.id.tvShowsFragment)
                    true
                }
                else -> false
            }
        })

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tmdb_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorites -> findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.favoritesFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}