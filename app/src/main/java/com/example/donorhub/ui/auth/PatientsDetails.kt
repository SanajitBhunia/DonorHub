package com.example.donorhub.ui.auth

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.databinding.PatientsDetailsBinding
import com.example.donorhub.model.ReceiverModel
import com.example.donorhub.ui.MainActivity2
import com.example.donorhub.utils.AddressUtils
import com.example.donorhub.utils.Config
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

import java.util.Locale

class PatientsDetails:AppCompatActivity() {
    private lateinit var binding: PatientsDetailsBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    lateinit var gender:String
    lateinit var patientBlood:String
     var units:Int=0
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
        val currentUser = auth.currentUser
        val password = intent.getStringExtra("password")




        val itemGender= arrayOf("Select Gender","Male","Female","Others")
        binding.spinnerGender.setAdapter(
            ArrayAdapter(
                this,
                R.layout.simple_spinner_dropdown_item,
                itemGender
            )
        )
        binding.spinnerGender.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent:AdapterView<*>?,
                    view: View?,
                    position:Int,
                    id:Long
                ){
                    gender=binding.spinnerGender.selectedItem.toString()
                }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        )


        val itemBlood =
            arrayOf("Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

        binding.spinnerBlood.setAdapter(
            ArrayAdapter(
                this,
                R.layout.simple_spinner_dropdown_item,
                itemBlood
            )
        )

        binding.spinnerBlood.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                patientBlood = binding.spinnerBlood.getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        val unitItem= arrayOf("Blood Units",1,2,3,4,5,6,7)
        binding.spinnerBloodUnit.setAdapter(
            ArrayAdapter(
                this,
                R.layout.simple_spinner_dropdown_item,
                unitItem
            )
        )

        binding.spinnerBloodUnit.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = binding.spinnerBloodUnit.selectedItem
                units = if (selectedItem is Int) {
                    selectedItem
                } else {
                    // Handle the case when the selected item is a String
                    // For example, set units to a default value
                    0 // Or any other default value you prefer
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        val itemsDivision = AddressUtils.getDivisions()

        binding.spinnerPatientDivsion.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                itemsDivision
            )
        )

        binding.spinnerPatientDivsion.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                patientDivision = binding.spinnerPatientDivsion.getSelectedItem().toString()
                binding.spinnerPatientDistricts.setAdapter(
                    ArrayAdapter(
                        this@PatientsDetails,
                        android.R.layout.simple_spinner_dropdown_item,
                        AddressUtils.getDistrict(patientDivision)
                    )
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

        binding.spinnerPatientDistricts.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                patientDistrict = binding.spinnerPatientDistricts.getSelectedItem().toString()



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        val itemReason= arrayOf("Select Reason","Surgery","Emergency Care","Pregnancy","Accident","Donation")

        binding.spinnerReason.setAdapter(
            ArrayAdapter(
                this,
                R.layout.simple_spinner_dropdown_item,
                itemReason
            )
        )
        binding.spinnerReason.setOnItemSelectedListener(object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                reason = binding.spinnerReason.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        )

        binding.postBtn.setOnClickListener {
            val patientname=binding.patientName.text.toString()
            val contactNumber=binding.contactNum.text.toString()
            val ageText=binding.patientAge.text.toString()
            val patientage=ageText.toIntOrNull()?:0
            val hospitalName=binding.HospitaName.text.toString()
            val hospitaladdress=binding.hospitalAddress.text.toString()
            val comments=binding.Comment.text.toString()


            if(patientname.isEmpty()){
                binding.patientName.setError("Please enter name")
                binding.patientName.requestFocus()
            }
           else if(contactNumber.isEmpty()){
                binding.contactNum.setError("Please enter a valid number")
                binding.contactNum.requestFocus()
            }
            else if(contactNumber.length!=10){
                binding.contactNum.setError("Phone number must be 10 digit")
                binding.contactNum.requestFocus()
            }
            else if(ageText.isEmpty()){
                binding.patientAge.setError("Please provide patient's age")
                binding.patientAge.requestFocus()
            }
            else if(hospitalName.isEmpty()){
                binding.HospitaName.setError("Please enter hospital name")
                binding.HospitaName.requestFocus()
            }
            else if(hospitaladdress.isEmpty()){
                binding.hospitalAddress.setError("Please enter hospital adress")
                binding.hospitalAddress.requestFocus()
            }
            else if(gender.equals("Select Gender")){
                Toast.makeText(this, "Please Provide Gender", Toast.LENGTH_SHORT).show()
            }
            else if(patientBlood.equals("Select Blood Group")){
                Toast.makeText(this, "Please Provide Blood Group", Toast.LENGTH_SHORT).show()
            }
            else if(binding.spinnerBloodUnit.equals("Blood Units")){
                Toast.makeText(this, "Please provide no of blood units", Toast.LENGTH_SHORT).show()
            }
            else if (binding.spinnerBloodUnit.selectedItemPosition == 0) {
                Toast.makeText(this, "Please Select Blood Units", Toast.LENGTH_SHORT).show()
            }else if (patientDivision.equals("Select Division")) {
                Toast.makeText(this, "Please Provide Division", Toast.LENGTH_SHORT).show()
            } else if (patientDistrict.equals("Select District")) {
                Toast.makeText(this, "Please Provide District", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.postBtn.isEnabled=true

                val currentUserId = auth.currentUser!!.uid
                val newDocumentRef = db.collection("Receiver").document()
                val newDocumentId = newDocumentRef.id

                val timestamp = Timestamp.now()
               // val dateString = formatDate(timestamp)

                // Create a data object with user information
                val data = ReceiverModel(
                    currentUserId,
                    patientname,
                    contactNumber,
                    patientage ,
                    gender,
                    patientBlood,
                    units,
                    hospitalName,
                    hospitaladdress,
                    patientDivision,
                    patientDistrict,
                    reason,
                    comments,
                    email = auth.currentUser!!.email ?: "",
                    password =password,
                    timestamp =timestamp
                )

//                db.collection("Receiver").document(currentUserId).set(data, SetOptions.merge())
//                    .addOnCompleteListener {
//                        if(it.isSuccessful) {
//                            startActivity(Intent(this, MainActivity2::class.java))
//                            finish()
//                            Toast.makeText(
//                                this,
//                                "Posted SuccessfulLy",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
                newDocumentRef.set(data)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity2::class.java))
                            finish()
                            Toast.makeText(
                                this,
                                "Posted Successfully",
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