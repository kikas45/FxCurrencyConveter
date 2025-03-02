package com.example.simplecomposeproject.RateCoverter.offlinehistory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecomposeproject.RateCoverter.offlinehistory.RatesModel
import com.example.simplecomposeproject.RateCoverter.offlinehistory.RatesModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RatesModelViewModel @Inject constructor(private val repository: RatesModelRepository) : ViewModel() {

    val readAllData: LiveData<List<RatesModel>> = repository.readAllData

    fun addRates(user: RatesModel) = viewModelScope.launch {
        repository.addRate(user)
    }

    fun updateUser(user: RatesModel) = viewModelScope.launch {
        repository.updateRate(user)
    }

    fun deleteUser(user: RatesModel) = viewModelScope.launch {
        repository.deleteRate(user)
    }

    fun deleteAllRates() = viewModelScope.launch {
        repository.deleteAlRates()
    }

}
