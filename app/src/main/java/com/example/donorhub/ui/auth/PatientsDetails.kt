package com.example.donorhub.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.databinding.PatientsDetailsBinding
import com.google.firebase.auth.FirebaseAuth

class PatientsDetails:AppCompatActivity() {
    private lateinit var binding: PatientsDetailsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=PatientsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}