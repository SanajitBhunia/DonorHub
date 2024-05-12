package com.example.donorhub.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donorhub.databinding.ActivityDonorReceiverBinding
import com.example.donorhub.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth


class Donor_receiver_activity : AppCompatActivity() {
    private lateinit var binding: ActivityDonorReceiverBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDonorReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()


            binding.btndonate.setOnClickListener {
                if(auth.currentUser!=null){
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else{
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        binding.btnrequest.setOnClickListener {
            startActivity(Intent(this,BloodRequest::class.java))
        }

    }
}