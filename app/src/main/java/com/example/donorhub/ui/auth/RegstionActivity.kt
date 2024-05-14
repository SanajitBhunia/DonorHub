package com.example.donorhub.ui.auth


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.model.DonorModel
import com.example.donorhub.databinding.ActivityRegstionBinding
import com.example.donorhub.databinding.SiginUpBinding

import com.example.donorhub.ui.MainActivity
import com.example.donorhub.utils.AddressUtils
import com.example.donorhub.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class RegstionActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegstionBinding
    lateinit var binding1: SiginUpBinding


    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    lateinit var blood: String
    lateinit var division: String
    lateinit var districts: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegstionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding1= SiginUpBinding.inflate(layoutInflater)
        val currentUser = auth.currentUser


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

        binding.saveBtn.setOnClickListener {


            val phone = binding.userPhone.text.toString()
            val name=binding.userName.text.toString()

            if(name.isEmpty()){
                    binding.userName.setError("Please enter name")
                    binding.userName.requestFocus()
            } else if (phone.isEmpty()) {
                binding.userPhone.setError("Please Enter your Phone")
                binding.userPhone.requestFocus()
            }  else if(phone.length!=10) {
                binding.userPhone.setError("Number must be 10 digit")
                binding.userPhone.requestFocus()
            }
                else if (blood.equals("Select Blood Group")) {
                Toast.makeText(this, "Please Provide Blood Group", Toast.LENGTH_SHORT).show()
            } else if (division.equals("Select Division")) {
                Toast.makeText(this, "Please Provide Division", Toast.LENGTH_SHORT).show()
            } else if (districts.equals("Select District")) {
                Toast.makeText(this, "Please Provide District", Toast.LENGTH_SHORT).show()
            } else {


                    val currentUserId = auth.currentUser!!.uid
                   val password = intent.getStringExtra("password")



                 // Create a data object with user information
                    val data = DonorModel(
                        currentUserId,
                        name,
                        phone,
                        blood,
                        division,
                        districts,
                        email = auth.currentUser!!.email ?: "",
                        password=password
                    )

                        db.collection("Donor").document(currentUserId).set(data,SetOptions.merge())
                            .addOnCompleteListener {
                                if(it.isSuccessful) {
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                    Toast.makeText(
                                        this,
                                        "Data Saved Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else{
                                    Config.hideDialog()
                                    Toast.makeText(this, "Unknown Error!! Please try again", Toast.LENGTH_SHORT).show()
                                }
                            }


            }

        }

    }


}