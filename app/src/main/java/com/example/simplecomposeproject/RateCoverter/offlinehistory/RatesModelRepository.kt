package com.example.simplecomposeproject.RateCoverter.offlinehistory

import androidx.lifecycle.LiveData
import javax.inject.Inject

class RatesModelRepository @Inject constructor(private val userDao: RatesDao) {
    val readAllData: LiveData<List<RatesModel>> = userDao.readAllData()

    suspend fun addRate(rates: RatesModel) = userDao.addRate(rates)
    suspend fun updateRate(rates: RatesModel) = userDao.updateRate(rates)
    suspend fun deleteRate(rates: RatesModel) = userDao.deleteRate(rates)
    suspend fun deleteAlRates() = userDao.deleteAllRates()
}
