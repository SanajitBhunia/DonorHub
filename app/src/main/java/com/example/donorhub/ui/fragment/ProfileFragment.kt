package com.example.donorhub.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.donorhub.databinding.FragmentProfileBinding
import com.example.donorhub.model.UserModel
import com.example.donorhub.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    private lateinit var currUser:UserModel

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

        Config.showDialog(requireContext())

        db.collection("users").document(currentUserId).get()
            .addOnSuccessListener { result ->

                currUser = result.toObject(UserModel::class.java)!!
                binding.userName.setText(currUser.name.toString())
                binding.userPhone.setText(currUser.phone.toString())
                binding.userEmail.setText(currUser.email.toString())
                binding.userBlood.setText(currUser.blood).toString()

                Config.hideDialog()

            }
        binding.updateBtn.setOnClickListener{
            val newName = binding.userName.text.toString()
            val newPhone = binding.userPhone.text.toString()

            db.collection("users").document(currentUserId).update(mapOf(
                "name" to newName,
                "phone" to newPhone
            )).addOnSuccessListener{
                Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
        }

    }

}