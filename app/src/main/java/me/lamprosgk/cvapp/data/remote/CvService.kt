package me.lamprosgk.cvapp.data.remote

import io.reactivex.Single
import me.lamprosgk.cvapp.model.Resume
import retrofit2.http.GET
import retrofit2.http.Path

interface CvService {

    // https://gist.githubusercontent.com/lamprosgk/adf8bab42d416815de1777386a62ab21/raw/dummy_cv.json

    @GET("{username}/{gist_id}/raw/{filename}")
    fun getCv(@Path("username") username: String, @Path("gist_id") gistId: String,
              @Path("filename") filename: String): Single<Resume>
}