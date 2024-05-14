package com.example.donorhub.model

import com.google.firebase.Timestamp
data class ReceiverModel(
    val id: String? = "",
    val name: String? = "",
    val contact: String? = "",
    val age:Int=0,
    val gender:String?="",
    val patientBlood: String? = "",
    val unit:Int=0,
    val hospitalName:String?="",
    val hospitalAdress:String?="",
    val division: String? = "",
    val district: String? = "",
    val reason:String?="",
    val comment:String?="",
    val email: String? = "",
    val password: String? = "",
    val timestamp:Timestamp
) {
    constructor() : this("", "", "", 0, "", "", 0,
        "", "", "", "",
        "", "", "", "", Timestamp.now())
}



