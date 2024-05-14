package com.example.donorhub.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import com.example.donorhub.R
import com.example.donorhub.databinding.FragmentRequestFragmentBinding
import com.example.donorhub.ui.auth.PatientsDetails


class request_fragment : Fragment() {

    lateinit var binding: FragmentRequestFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailsButton.setOnClickListener {
            val intent=Intent(requireContext(), PatientsDetails::class.java)
            startActivity(intent)
        }
    }

}