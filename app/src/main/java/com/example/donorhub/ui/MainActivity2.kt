package com.example.donorhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.donorhub.R
import com.example.donorhub.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityMain2Binding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        // After setContentView(binding.root)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout2,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout2.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_bar)
        binding.bottomNavigationView2.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            title = when (destination.id) {
                R.id.request -> "My Request"
                R.id.searchdonor -> "Search Donor"
                R.id.notification ->"Notification"
                else ->"Blood"
            }
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.drawerLayout2.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout2.close()
        } else super.onBackPressed()
    }

}