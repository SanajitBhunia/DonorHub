package com.example.donorhub.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.databinding.FillTheDetailsBinding

class BloodRequest:AppCompatActivity() {
        private lateinit var binding: FillTheDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FillTheDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.detailsBtn.setOnClickListener {
            startActivity(Intent(this,PatientsDetails::class.java))
        }
    }
}