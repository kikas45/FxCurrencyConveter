package com.example.simplecomposeproject.RateCoverter.fxdates.api

data class MoneyResponse(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)