package me.lamprosgk.cvapp.model


import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String
)