package com.example.donorhub.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.donorhub.R
import com.example.donorhub.databinding.ActivityIntroBinding


class IntroActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntroBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences=getSharedPreferences("DonorHub", Context.MODE_PRIVATE)

        displayRandomQuote()
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this,Donor_receiver_activity::class.java ))
            finish()
        },4000)



    }
    private fun displayRandomQuote() {
        val quotesArray = resources.getStringArray(R.array.blood_donation_quotes)
        val randomIndex = (0 until quotesArray.size).random()
        val randomQuote = quotesArray[randomIndex]

        // Set the random quote to the TextView using View Binding
        binding.textView.text = randomQuote
    }
}