package com.example.donorhub.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.donorhub.databinding.ActivityReceiverDetailsBinding
import com.example.donorhub.model.ReceiverModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ReceiverDetails : AppCompatActivity() {
    private lateinit var binding: ActivityReceiverDetailsBinding
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityReceiverDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=FirebaseFirestore.getInstance()
        auth=FirebaseAuth.getInstance()




        val receiverId = intent.getStringExtra("receiverId")
        Toast.makeText(this, "$receiverId", Toast.LENGTH_SHORT).show()
        val newDocumentRef = db.collection("Receiver").document()
        val newDocumentId = newDocumentRef.id

        // Check if receiverId is not null
        if (receiverId != null) {
            // Fetch receiver details from Firestore
            db.collection("Receiver").document(newDocumentId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val receiver = documentSnapshot.toObject(ReceiverModel::class.java)
                        if (receiver != null) {
                            // Populate UI with receiver details
                            binding.reciverName.text=receiver.name.toString()
                            binding.receiverHospitalName.setText("${receiver.hospitalName}")
                            binding.receiverHospital.setText(" ${receiver.hospitalAdress}")
                            binding.receiverDistrict.setText("${receiver.district}")
                            binding.receiverReason.setText("${receiver.reason}")
                            binding.receiverContact.setText("${receiver.contact}")
                            binding.receiverBloodGroup.setText("${receiver.patientBlood}")
                            binding.receiverBloodUnits.setText("${receiver.unit}")
                            binding.receiverAge.setText("${receiver.age}")
                            binding.receiverGender.setText("${receiver.gender}")
                            binding.receiverComment.setText("${receiver.comment}")
                            // Populate other UI elements similarly
                            binding.btnCall.setOnClickListener {
                                val phoneNumber = receiver.contact // Replace this with the actual receiver's contact number

                                val intent = Intent(Intent.ACTION_DIAL)
                                intent.data = Uri.parse("tel:$phoneNumber")
                                startActivity(intent)
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle failure to fetch receiver details
                }
        }
    }
}

