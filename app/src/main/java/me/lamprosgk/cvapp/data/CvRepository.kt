package me.lamprosgk.cvapp.data

import io.reactivex.Single
import me.lamprosgk.cvapp.model.Resume

interface CvRepository {

    fun getCv(githubUsername: String, gistId: String, filename: String): Single<Resume>
}