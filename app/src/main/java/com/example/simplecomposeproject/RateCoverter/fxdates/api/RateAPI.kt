package com.example.simplecomposeproject.RateCoverter.fxdates.api

import com.example.simplecomposeproject.RateCoverter.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RateAPI {

    @GET("/latest?access_key=${Constants.API_KEY}")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<MoneyResponse>


}