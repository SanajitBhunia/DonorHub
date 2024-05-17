package com.example.donorhub.ui.fragment

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.text.Editable
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
            val newName = edtName.text.toString().trim()
            val newAge = edtAge.text.toString().toIntOrNull() ?: -1 // Use -1 as an invalid age indicator

            val selectedBloodGroup = spinnerBloodGroup.selectedItem.toString()

            // Map to store the updates
            val updates = mutableMapOf<String, Any>()

            // Add fields to update map only if they are valid
            if (newName.isNotBlank()) {
                updates["name"] = newName
            }
            if (newAge in 18..65) {
                updates["age"] = newAge
            }
            if (selectedBloodGroup.isNotBlank()) {
                updates["blood"] = selectedBloodGroup
            }

            if (updates.isNotEmpty()) {
                // Perform update operation
                db.collection("Donor").document(auth.currentUser!!.uid)
                    .update(updates)
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            "Profile updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        alertDialog.dismiss()

                        // Update local user object and UI
                        updates.forEach { (key, value) ->
                            when (key) {
                                "name" -> {
                                    // Update your local user object and UI here, e.g., user.name = value as String
                                    currUser.name = value as String
                                    binding.userName.text = currUser.name!!.toEditable()
                                }
                                "age" -> {
                                    // Update your local user object and UI here, e.g., user.age = value as Int
                                    currUser.age=value as Int
                                    binding.userAge.text=currUser.age.toString().toEditable()
                                }
                                "blood" -> {
                                    // Update your local user object and UI here, e.g., user.bloodGroup = value as String
                                    currUser.blood=value as String
                                    binding.userBlood.text= currUser.blood!!.toEditable()
                                }
                            }
                        }

                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            requireContext(),
                            "Failed to update profile: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Please fill at least one detail to update", Toast.LENGTH_SHORT).show()
            }
        }


        alertDialog.show()
    }
    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


}