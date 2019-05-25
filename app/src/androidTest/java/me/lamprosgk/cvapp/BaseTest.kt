package me.lamprosgk.cvapp

import com.google.gson.Gson
import me.lamprosgk.cvapp.model.Resume

open class BaseTest {

    // return static json from resources
    private val json = readAsString("cv_response.json")

    val successResponse: Resume = Gson().fromJson<Resume>(json, Resume::class.java)

    private fun readAsString(path: String): String {
        return this.javaClass.classLoader.getResourceAsStream(path).bufferedReader().use { it.readText() }


    }
}

