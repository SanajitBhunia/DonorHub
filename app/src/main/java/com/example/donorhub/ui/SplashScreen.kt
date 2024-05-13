package com.example.donorhub.ui

import android.app.TaskStackBuilder
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.donorhub.R
import com.example.donorhub.ui.auth.LoginActivity
import com.example.donorhub.ui.auth.IntroActivity
import com.example.donorhub.ui.auth.Signup
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    lateinit var editor: Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        preferences = getSharedPreferences("splash", MODE_PRIVATE)
        editor = preferences.edit()


        Handler(Looper.myLooper()!!).postDelayed(Runnable {

            if (FirebaseAuth.getInstance().currentUser == null) {
                preferences.getBoolean("isMain", true)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                editor.putBoolean("isMain", true)
                editor.apply()

                TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(Intent(this, MainActivity::class.java))
                    .addNextIntent(Intent(this, IntroActivity::class.java))
                    .startActivities()
            }

        }, 1000)
    }

}