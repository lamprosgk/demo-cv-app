package me.lamprosgk.cvapp.model


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    val city: String,
    @SerializedName("post_code")
    val postCode: String,
    @SerializedName("street")
    val street: String
)