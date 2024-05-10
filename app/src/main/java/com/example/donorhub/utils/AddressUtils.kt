package com.example.donorhub.utils

object AddressUtils {
    private val divisions = listOf(
        "Select Division",
        "Presidency",
        "Medinipur",
        "Burdwan",
        "Malda",
        "Jalpaiguri",
    )

    private val districts = hashMapOf(
        divisions[0] to listOf("Select District"),
        divisions[1] to listOf(
            "Select District",
            "Howrah",
            "Kolkata",
            "Nadia",
            "North 24 Parganas",
            "South 24 Parganas",

        ),
        divisions[2] to listOf(
            "Select District",
            "Bankura",
            "Jhargram",
            "Paschim Medinipur",
            "Purba Medinipur",
            "Purulia",
        ),
        divisions[3] to listOf(
            "Select District",
            "Birbhum",
            "Paschim Bardhaman",
            "Purba Bardhaman",
            "Hooghly",
        ),
        divisions[4] to listOf(
            "Select District",
            "Dakshin Dinajpur",
            "Uttar Dinajpur",
            "Malda",
            "Murshidabad",

        ),
        divisions[5] to listOf(
            "Select District",
            "Alipurduar",
            "Cooch Behar",
            "Darjeeling",
            "Jalpaiguri",
            "Kalimpong",

        ),

    )

//    private val thanas = hashMapOf(
//        "Abul" to listOf("THana", "kana"),
//        "Kabul" to listOf("KabulTHana", "Kabulkana")
//    )

    fun getDivisions(): List<String> = divisions
    fun getDistrict(division: String): List<String> = districts.get(division) ?: listOf()

//    fun getThan(district: String): List<String> = thanas[district] ?: listOf()
}