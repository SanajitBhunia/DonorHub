package com.example.donorhub.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.donorhub.databinding.ItemUserBinding
import com.example.donorhub.databinding.RequestItemBinding
import com.example.donorhub.model.DonorModel
import com.example.donorhub.model.ReceiverModel
import com.example.donorhub.ui.auth.ForgotActivity
import com.example.donorhub.ui.auth.ReceiverDetails
import com.example.donorhub.ui.fragment.search_donor
import java.text.SimpleDateFormat
import java.util.Locale

class ReceiverAdapter(val context:search_donor,var list: ArrayList<ReceiverModel>):
RecyclerView.Adapter<ReceiverAdapter.UserViewHolder>(){


    class UserViewHolder(val binding: RequestItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return ReceiverAdapter.UserViewHolder(
            RequestItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    fun updateData(dataItem: ArrayList<ReceiverModel>) {
        list = dataItem
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder:UserViewHolder, position: Int) {
        holder.binding.bloodGroup.text=list[position].patientBlood.toString()
        holder.binding.hospitalName.text=list[position].hospitalName.toString()
        val timestamp = list[position].timestamp
        val date = timestamp.toDate()
        val sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val formattedDate = sdf.format(date)
        holder.binding.postedDate.text = formattedDate

        holder.binding.animationView.setOnClickListener {
            val phone = list[position].contact.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL
            intent.data =
                Uri.parse("tel: $phone")
            context.startActivity(intent)
        }
        holder.binding.patientsDetails.setOnClickListener {
            val intent = Intent(context.requireContext(), ReceiverDetails::class.java)
            context.startActivity(intent)

        }
    }




    fun setAnimation(view: View) {
        val animation: Animation = AnimationUtils.loadAnimation(context.requireContext(), android.R.anim.slide_in_left)
        view.animation = animation
    }

    override fun getItemCount() =list.size

}