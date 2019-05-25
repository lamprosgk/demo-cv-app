package me.lamprosgk.cvapp.model


import com.google.gson.annotations.SerializedName

data class Period(
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String
)