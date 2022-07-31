package com.surajrathod.codingcontests.network

import com.surajrathod.codingcontests.model.ContestList
import retrofit2.http.GET


const val BASE_URL = "https://kontests.net/api/v1/"
interface ApiInterface {

    @GET(BASE_URL+"all")
    suspend fun getAllSites() : ContestList


}