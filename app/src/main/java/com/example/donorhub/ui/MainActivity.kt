package com.example.donorhub.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.donorhub.R
import com.example.donorhub.databinding.ActivityMainBinding
import com.example.donorhub.ui.auth.LocationActivity
import com.example.donorhub.ui.auth.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.navigationView.setNavigationItemSelectedListener(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_menu)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)



        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            title = when (destination.id) {
                R.id.homeFragment -> "Home"
                R.id.profileFragment -> "Profile"
                else -> "Blood"
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                val appLink = """
                    https://play.google.com/store/apps/details?id=${this.getPackageName()}
                        """.trimIndent()
                val sendInt = Intent(Intent.ACTION_SEND)
                sendInt.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.app_name))
                sendInt.putExtra(
                    Intent.EXTRA_TEXT,
                    this.getString(R.string.app_name).toString() + appLink
                )
                sendInt.type = "text/plain"
                this.startActivity(Intent.createChooser(sendInt, "Share"))
            }

            R.id.developer -> {
                startActivity(Intent(this, DeveloperActivity::class.java))
            }

            R.id.location -> {
                startActivity(Intent(this, LocationActivity::class.java))
            }

            R.id.map -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:22.5726,88.3639?q=Kolkata, West Bengal, India"))
                intent.setPackage("com.google.android.apps.maps") // Specify the package to ensure it opens in Google Maps
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {

                }
            }

            R.id.logout -> {
                AlertDialog.Builder(this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to Logout?")
                    .setPositiveButton("Log Out") { _, _ ->
                        auth.signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .setNegativeButton("Cancel"){dialog,_->
                       dialog.dismiss()

                    }.show()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.close()
        } else super.onBackPressed()
    }
}