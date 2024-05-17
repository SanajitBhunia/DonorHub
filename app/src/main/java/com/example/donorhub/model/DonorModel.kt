package com.example.donorhub.model

data class DonorModel(
    val id: String? = "",
    var name: String? = "",
    val phone: String? = "",
    var blood: String? = "",
    var age:Int=18,
    val division: String? = "",
    val district: String? = "",
    val email: String? = "",
    val password: String? = "",
)
