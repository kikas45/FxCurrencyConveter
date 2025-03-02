package com.example.simplecomposeproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.simplecomposeproject.RateCoverter.ActivityLayouts.OfflineRateHistoryScreen
import com.example.simplecomposeproject.RateCoverter.offlinehistory.RatesModel
import com.example.simplecomposeproject.RateCoverter.offlinehistory.viewmodel.RatesModelViewModel
import com.example.simplecomposeproject.theme.SImpleComposeProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfflineRateHistoryActivity : ComponentActivity() {
    // Get the ViewModel instance
    private val ratesViewModel: RatesModelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SImpleComposeProjectTheme {
                // Observe saved Rates from the database
                val savedRates by ratesViewModel.readAllData.observeAsState(initial = emptyList<RatesModel>())
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OfflineRateHistoryScreen(
                        items = savedRates,
                        onBackClicked = { finish() },
                        onDeleteAllClicked = { ratesViewModel.deleteAllRates() },
                        onItemClicked = { clickedItem ->
                            // Construct the text string using clickedItem values.
                            val textDisplay = "${clickedItem.time}\n${clickedItem.from} == ${clickedItem.to} @ ${clickedItem.rate}"
                            Toast.makeText(this@OfflineRateHistoryActivity, textDisplay, Toast.LENGTH_SHORT).show()
                        },
                        showDeleteAll = savedRates.isNotEmpty(),
                        errorMessageVisible = savedRates.isEmpty()
                    )
                }
            }
        }
    }
}
