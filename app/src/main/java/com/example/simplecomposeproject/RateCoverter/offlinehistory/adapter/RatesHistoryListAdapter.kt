package com.example.simplecomposeproject.RateCoverter.offlinehistory.adapter
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.simplecomposeproject.RateCoverter.ActivityLayouts.RateHistoryItemView
import com.example.simplecomposeproject.RateCoverter.offlinehistory.RatesModel

@Composable
fun RatesHistoryListAdapter(
    items: List<RatesModel>,
    onItemClicked: (RatesModel) -> Unit
) {
    LazyColumn {
        items(items) { item ->
            // Format the display text as needed
            val textDisplay = "${item.time}\n${item.from} == ${item.to} @ ${item.rate}"
            RateHistoryItemView(
                text = textDisplay,
                onClick = { onItemClicked(item) }
            )
        }
    }
}
