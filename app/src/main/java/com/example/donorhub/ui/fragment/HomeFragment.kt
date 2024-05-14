package com.example.donorhub.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.donorhub.adapter.DonorAdapter
import com.example.donorhub.databinding.FragmentHomeBinding
import com.example.donorhub.model.DonorModel
import com.example.donorhub.utils.Config
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding
    lateinit var db: FirebaseFirestore

    private lateinit var list: ArrayList<DonorModel>
    private lateinit var adapter: DonorAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        list = ArrayList()
        adapter = DonorAdapter(this, list)

        db = FirebaseFirestore.getInstance()

        Config.showDialog(requireContext())
        db.collection("Donor").addSnapshotListener { value, error ->
            val list = arrayListOf<DonorModel>()
            val data = value?.toObjects(DonorModel::class.java)
            list.addAll(data!!)

            binding.userRecyclerView.adapter = DonorAdapter(this, list)
            adapter.updateData(list)

           adapter.updateData(list)

            Config.hideDialog()
        }

        searchData()

        return binding.root
    }

    lateinit var searchText: String

    private fun searchData() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                searchText = s.toString().lowercase()

                updateRecylerView()
            }
        })
    }


    private fun updateRecylerView() {
        val data = ArrayList<DonorModel>()

        for (item in list) {

            var coinName = item.phone!!.lowercase(Locale.getDefault())

            if (coinName.contains(searchText)){
                data.add(item)
            }
            
        }
        adapter.updateData(data)
    }

}