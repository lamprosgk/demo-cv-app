package me.lamprosgk.cvapp

import io.reactivex.Single
import me.lamprosgk.cvapp.model.*
import okhttp3.ResponseBody
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response


// mock resume
const val firstName = "Lampros"
const val lastName = "Gkotsoulias"
private val address = Address("London", "NZ1 99X", "123 High Street")
private val contactDetails = ContactDetails("lampros@mail.com", "0678956789")
val skills = listOf("Java", "Kotlin", "RxJava", "Dagger")
private const val summary = "Circus acrobat with 10 years experience."

private val roles =
    listOf(WorkExperience(Company("London", "TheAmazingCompany"), Period("2015", "2019"), "Senior Engineer"))
val resume = Resume(address, contactDetails, firstName, lastName, skills, summary, roles)

// cast null to a type, to bypass Mockito's any() not allowed to return null
fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T

val errorObservable: Single<Resume> =
    Single.error(
        HttpException(
            Response.error<Resume>(
                500,
                ResponseBody.create(null, "Internal Server Error")
            )
        )
    )
