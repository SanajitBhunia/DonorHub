package com.example.donorhub.ui.auth

import android.R
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.databinding.PatientsDetailsBinding
import com.example.donorhub.utils.AddressUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.FieldPosition

class PatientsDetails:AppCompatActivity() {
    private lateinit var binding: PatientsDetailsBinding
    private lateinit var auth: FirebaseAuth
    private  lateinit var db:FirebaseFirestore

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


    }
}