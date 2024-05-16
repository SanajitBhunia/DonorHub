package com.example.donorhub.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.donorhub.R
import com.example.donorhub.adapter.DonorAdapter
import com.example.donorhub.adapter.ReceiverAdapter
import com.example.donorhub.databinding.FragmentSearchDonorBinding
import com.example.donorhub.model.DonorModel
import com.example.donorhub.model.ReceiverModel
import com.example.donorhub.utils.Config
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale


class search_donor : Fragment() {

    lateinit var binding: FragmentSearchDonorBinding
    lateinit var db:FirebaseFirestore

    lateinit var list:ArrayList<ReceiverModel>
    private lateinit var adapter:ReceiverAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding=FragmentSearchDonorBinding.inflate(layoutInflater)

        list = ArrayList()
        adapter = ReceiverAdapter(this, list)

        db = FirebaseFirestore.getInstance()


        Config.showDialog(requireContext())
        db.collection("Receiver").addSnapshotListener { value, error ->
            val list = arrayListOf<ReceiverModel>()
            val data = value?.toObjects(ReceiverModel::class.java)
            list.addAll(data!!)

            binding.userRecyclerView.adapter = ReceiverAdapter(this, list)
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
        val data = ArrayList<ReceiverModel>()

        for (item in list) {

            var coinName = item.contact!!.lowercase(Locale.getDefault())

            if (coinName.contains(searchText)){
                data.add(item)
            }

        }
        adapter.updateData(data)
    }



}