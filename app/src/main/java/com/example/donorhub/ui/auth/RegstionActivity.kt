package com.example.donorhub.ui.auth


import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.R
import com.example.donorhub.model.UserModel
import com.example.donorhub.databinding.ActivityRegstionBinding

import com.example.donorhub.ui.MainActivity
import com.example.donorhub.utils.AddressUtils
import com.example.donorhub.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.collection.LLRBNode.Color
import com.google.firebase.firestore.FirebaseFirestore


class RegstionActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegstionBinding


    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    lateinit var blood: String
    lateinit var division: String
    lateinit var districts: String
    lateinit var role:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegstionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.userLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.verifyBtn1.setOnClickListener {
            val phone = binding.userPhone.text!!.toString()
            if(phone.isEmpty()) {
                Toast.makeText(this,"Enter a Valid Phone Number ",Toast.LENGTH_LONG).show()

            }
             else if(phone.length!=10){
                Toast.makeText(this,"Enter a Valid 10 digits Phone Number ",Toast.LENGTH_LONG).show()
            }
            else{
                var intent=Intent(this,OTPActivity::class.java)
                intent.putExtra("number",phone)
                startActivity(intent)


            }
        }


        val item =
            arrayOf("Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

        binding.spinnerBloodGroup.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                item
            )
        )

        binding.spinnerBloodGroup.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                blood = binding.spinnerBloodGroup.getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })



        val items = AddressUtils.getDivisions()

        binding.spinnerDivsion.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
            )
        )

        binding.spinnerDivsion.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                division = binding.spinnerDivsion.getSelectedItem().toString()
                binding.spinnerDistricts.setAdapter(
                    ArrayAdapter(
                        this@RegstionActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        AddressUtils.getDistrict(division)
                    )
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })



        binding.spinnerDistricts.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                districts = binding.spinnerDistricts.getSelectedItem().toString()

//                val tahsns = AddressUtils.getThan(districts)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        binding.signIn.setOnClickListener {

            val name = binding.userName.text.toString()
            val phone = binding.userPhone.text.toString()
            val email = binding.userEmail.text.toString()
            val password = binding.userPassword.text.toString()

            if (name.isEmpty()) {
                binding.userName.setError("Please Enter your Name")
                binding.userName.requestFocus()
            } else if (phone.isEmpty()) {
                binding.userPhone.setError("Please Enter your Phone")
                binding.userPhone.requestFocus()
            }  else if(phone.length!=10) {
                binding.userPhone.setError("Number must be 10 digit")
                binding.userPhone.requestFocus()

            } else if (email.isEmpty()) {
                binding.userEmail.setError("Please Enter your Email")
                binding.userEmail.requestFocus()
            } else if (password.isEmpty()) {
                binding.userPassword.setError("Please Enter your Password")
                binding.userPassword.requestFocus()
            } else if(role.equals("Are you a..")){
                Toast.makeText(this, "Please specify Are yoa a DONOR or RECEIVER", Toast.LENGTH_SHORT).show()
            } else if (blood.equals("Select Blood Group")) {
                Toast.makeText(this, "Please Provide Blood Group", Toast.LENGTH_SHORT).show()
            } else if (division.equals("Select Division")) {
                Toast.makeText(this, "Please Provide Division", Toast.LENGTH_SHORT).show()
            } else if (districts.equals("Select District")) {
                Toast.makeText(this, "Please Provide District", Toast.LENGTH_SHORT).show()
            } else {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Config.showDialog(this)
                        val currentUserId = auth.currentUser!!.uid
                        val data = UserModel(
                            currentUserId,
                            name,
                            phone,
                            blood,
                            division,
                            districts,
                            email,
                            password
                        )

                        db.collection("users").document(currentUserId).set(data)
                            .addOnCompleteListener {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Config.hideDialog()
                        Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, "Registration UnSuccessful", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }


}