package com.example.simplecomposeproject.RateCoverter.fxdates

import com.example.simplecomposeproject.RateCoverter.fxdates.api.MoneyResponse
import com.example.simplecomposeproject.RateCoverter.fxdates.api.RateAPI
import retrofit2.Response
import javax.inject.Inject


class RateRepository @Inject constructor(private val api: RateAPI) {

    suspend fun getRates(base: String): Response<MoneyResponse> {
        return api.getRates(base)
    }
}
