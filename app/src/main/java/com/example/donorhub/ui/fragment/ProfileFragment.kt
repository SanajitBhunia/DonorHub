package com.example.donorhub.ui.fragment

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.donorhub.R
import com.example.donorhub.databinding.FragmentProfileBinding
import com.example.donorhub.model.DonorModel
import com.example.donorhub.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    private lateinit var currUser:DonorModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val currentUserId = auth.currentUser!!.uid
        binding.fabEdit.setOnClickListener{
            showUpdateProfileDialog()
        }

        Config.showDialog(requireContext())

        db.collection("Donor").document(currentUserId).get()
            .addOnSuccessListener { result ->

                currUser = result.toObject(DonorModel::class.java)!!
                binding.userName.setText(currUser.name.toString())
                binding.userPhone.setText(currUser.phone.toString())
                binding.userEmail.setText(currUser.email.toString())
                binding.userBlood.setText(currUser.blood).toString()
                binding.userAge.setText(currUser.age.toString())


                Config.hideDialog()

            }

    }
    private fun showUpdateProfileDialog() {
        val dialogView = layoutInflater.inflate(R.layout.update_profile_donor, null)
        val edtName = dialogView.findViewById<EditText>(R.id.edtprofileupdate)
        val edtAge = dialogView.findViewById<EditText>(R.id.edt2updateprofile)
        val spinnerBloodGroup = dialogView.findViewById<Spinner>(R.id.spinprofile)
        val btnUpdate = dialogView.findViewById<Button>(R.id.btnupdateprofile)

        // Populate blood group spinner
        val bloodGroups = arrayOf("Select any","A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, bloodGroups)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBloodGroup.adapter = adapter

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Update Profile")
            .create()

        btnUpdate.setOnClickListener {
            val newName = edtName.text.toString()
            val newAge = edtAge.text.toString().toIntOrNull() ?: 0 // Convert age string to int

            if (newName.isNotBlank() ){
                if(newAge in 18..65) {
                    val selectedBloodGroup = spinnerBloodGroup.selectedItem.toString()

                    // Perform update operation
                    db.collection("Donor").document(auth.currentUser!!.uid)
                        .update(
                            mapOf(
                                "name" to newName,
                                "age" to newAge,
                                "blood" to selectedBloodGroup
                            )
                        )
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "Profile updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            alertDialog.dismiss()
                            // Update local user object and UI if needed

                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                requireContext(),
                                "Failed to update profile: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            } else {
                Toast.makeText(requireContext(), "The donors age should be between 18 to 65 ", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialog.show()
    }


}