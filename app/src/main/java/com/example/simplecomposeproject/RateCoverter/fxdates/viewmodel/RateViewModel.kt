package com.example.simplecomposeproject.RateCoverter.fxdates.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecomposeproject.RateCoverter.fxdates.RateRepository
import com.example.simplecomposeproject.RateCoverter.fxdates.api.MoneyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class RateViewModel @Inject constructor(private val repository: RateRepository) : ViewModel() {

    private val _rates = MutableLiveData<MoneyResponse?>()
    val rates: LiveData<MoneyResponse?> = _rates

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchRates(base: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { repository.getRates(base) }
                if (response.isSuccessful) {
                    _rates.value = response.body()
                    setError("") // Clear error on success.
                } else {
                    setError("Error fetching rates: ${response.message()}")
                }
            } catch (e: Exception) {
                setError("An error occurred: ${e.localizedMessage}")
            } finally {
                _loading.value = false
            }
        }
    }

    // Public function to update error.
    fun setError(message: String) {
        _error.postValue(message)
    }
}
