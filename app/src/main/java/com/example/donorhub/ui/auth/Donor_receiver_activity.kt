package com.example.donorhub.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donorhub.databinding.ActivityDonorReceiverBinding
import com.example.donorhub.ui.MainActivity
import com.example.donorhub.ui.MainActivity2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Donor_receiver_activity : AppCompatActivity() {
    private lateinit var binding: ActivityDonorReceiverBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDonorReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        val password = intent.getStringExtra("password")


        binding.btndonate.setOnClickListener {
            intent.putExtra("password", password)
            checkUserData()

        }
        binding.btnrequest.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
            intent.putExtra("password", password)
        }

    }

    private fun checkUserData() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            firestore.collection("Donor").document(user.uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // User data exists, proceed to another activity
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // User data doesn't exist, navigate to registration activity
                        startActivity(Intent(this, DonorDetails::class.java))
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle any errors
                }
        }
    }
}