package com.example.safetea2.api

import com.google.gson.annotations.SerializedName

data class CrimeometerResponse(
    @SerializedName("data") val data: List<FBI_CrimeData>
)


data class FBI_CrimeData(
    @SerializedName("violent_crime") val violentCrime: Int?
)