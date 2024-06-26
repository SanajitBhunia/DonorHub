package com.example.donorhub.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.donorhub.databinding.ItemUserBinding
import com.example.donorhub.model.DonorModel
import com.example.donorhub.ui.fragment.HomeFragment

class DonorAdapter(val context: HomeFragment, var list: ArrayList<DonorModel>) :
    RecyclerView.Adapter<DonorAdapter.UserViewHolder>() {

    class UserViewHolder (val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    fun updateData(dataItem: ArrayList<DonorModel>) {
        list = dataItem
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.binding.userName.text = list[position].name.toString()
        holder.binding.userBlood.text = list[position].blood.toString()
        holder.binding.userDivision.text = list[position].division.toString()
        holder.binding.userDistrict.text = list[position].district.toString()
        holder.binding.userPhone.text = list[position].phone.toString()



        holder.binding.animationView.setOnClickListener {
            val phone = list[position].phone.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL
            intent.data =
                Uri.parse("tel: $phone")
            context.startActivity(intent)
        }

       setAnimation(holder.binding.root)

    }

    fun setAnimation(view: View) {
        val animation: Animation = AnimationUtils.loadAnimation(context.requireContext(), android.R.anim.slide_in_left)
        view.animation = animation
    }

    override fun getItemCount() = list.size
}
