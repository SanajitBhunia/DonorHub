package com.example.donorhub.model

data class DonorModel(
    val id: String? = "",
    val name: String? = "",
    val phone: String? = "",
    val blood: String? = "",
    val age:Int=18,
    val division: String? = "",
    val district: String? = "",
    val email: String? = "",
    val password: String? = "",
)
