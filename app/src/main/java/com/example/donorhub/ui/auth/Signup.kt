package com.example.donorhub.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.databinding.SiginUpBinding
import com.example.donorhub.ui.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Signup:AppCompatActivity() {

    lateinit var binding: SiginUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=SiginUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=Firebase.auth

        binding.userLogin2.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.signup.setOnClickListener {
            val email=binding.useremail.text.toString()
            val password=binding.userPassword.text.toString()
            if(email.isEmpty()){
                binding.useremail.setError("Invalid Email Address")
                binding.useremail.requestFocus()
             }
            else if (password.isEmpty()) {
            binding.userPassword.setError("Please Enter your Password")
            binding.userPassword.requestFocus()
            }
            else if(password.length<8){
                binding.userPassword.setError("Please Enter 8 Character Password")
                binding.userPassword.requestFocus()
            }
            else{
                    CreateUser()
            }
        }
    }

    private fun CreateUser() {
        val email = binding.useremail.text.toString()
        val password = binding.userPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Donor_receiver_activity::class.java))
                    finish()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(this, "Registration UnSuccessful", Toast.LENGTH_SHORT).show()

                }
            }
    }


}