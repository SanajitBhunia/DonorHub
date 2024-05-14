package com.example.donorhub.ui.auth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.R
import com.example.donorhub.databinding.ActivityLoginBinding
import com.example.donorhub.databinding.DonorDetailsBinding
import com.example.donorhub.databinding.OtpDialogueVerificationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPActivity: AppCompatActivity() {

      private lateinit var binding: OtpDialogueVerificationBinding
      private lateinit var binding1:DonorDetailsBinding
     lateinit var auth:FirebaseAuth
    private lateinit var  verificationId : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OtpDialogueVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //supportActionBar?.hide()

        binding1 =DonorDetailsBinding.inflate(layoutInflater)

        auth=FirebaseAuth.getInstance()


        val phoneNumber= "+91"+intent.getStringExtra("number")
        binding.mobilenotxtview.text=phoneNumber

        val options=PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object :PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(this@OTPActivity, "Try again!!  ${p0}", Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    verificationId=p0
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.verifyBtn2.setOnClickListener {
            if(binding.otp.text!!.isEmpty()){
                Toast.makeText(this,"Please enter OTP",Toast.LENGTH_LONG).show()
            }
            else{
                val credentials=PhoneAuthProvider.getCredential(verificationId,binding.otp.text.toString())

                auth.signInWithCredential(credentials)
                    .addOnCompleteListener(this){
                        if(it.isSuccessful){
                           // Log.d(TAG, "signInWithCredential:success")
                            binding1.verifyBtn1.text = "VERIFIED"
                            Toast.makeText(this,"Verification Successful",Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Verification failed",Toast.LENGTH_LONG).show()
                        }

                    }
            }
        }
    }

}