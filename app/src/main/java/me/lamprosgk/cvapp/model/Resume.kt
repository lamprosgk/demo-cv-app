package me.lamprosgk.cvapp.model


import com.google.gson.annotations.SerializedName

data class Resume(
    @SerializedName("address")
    val address: Address,
    @SerializedName("contact_details")
    val contactDetails: ContactDetails,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("skills")
    val skills: List<String>,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("work_experience")
    val workExperience: List<WorkExperience>
)