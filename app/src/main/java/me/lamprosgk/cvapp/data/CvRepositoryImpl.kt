package me.lamprosgk.cvapp.data

import me.lamprosgk.cvapp.data.remote.CvService
import javax.inject.Inject

class CvRepositoryImpl @Inject constructor(private val CvService: CvService) : CvRepository {


    override fun getCv(githubUsername: String, gistId: String, filename: String) = CvService.getCv(githubUsername, gistId, filename)
}