package com.example.donorhub.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.databinding.PatientsDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PatientsDetails:AppCompatActivity() {
    private lateinit var binding: PatientsDetailsBinding
    private lateinit var auth: FirebaseAuth
    private  lateinit var db:FirebaseFirestore

    lateinit var gender:String
    lateinit var patientBlood:String
     var units:Int = 0
    lateinit var patientDivision:String
    lateinit var patientDistrict:String
    lateinit var reason:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=PatientsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        db=FirebaseFirestore.getInstance()
        auth=FirebaseAuth.getInstance()


    }
}