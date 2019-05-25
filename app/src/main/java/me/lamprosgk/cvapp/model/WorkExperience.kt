package me.lamprosgk.cvapp.model

import com.google.gson.annotations.SerializedName

data class WorkExperience(
    @SerializedName("company")
    val company: Company,
    @SerializedName("period")
    val period: Period,
    @SerializedName("role")
    val role: String
)