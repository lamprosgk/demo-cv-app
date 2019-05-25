package me.lamprosgk.cvapp.model


import com.google.gson.annotations.SerializedName

data class ContactDetails(
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String
)