package com.example.simplecomposeproject.RateCoverter.DialogLayouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hbb20.R

@Composable
fun SelectCurrencyToDialog(
    onCurrencySelected: (CurrencyOption) -> Unit,
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Select Currency To Convert To",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF00008B)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(16.dp))
                // List of currency options (adjust resource IDs as needed)
                val currencies = listOf(
                    CurrencyOption("EUR", R.drawable.flag_netherlands),
                    CurrencyOption("USD", R.drawable.flag_united_states_of_america),
                    CurrencyOption("GBP", R.drawable.flag_united_kingdom),
                    CurrencyOption("AUD", R.drawable.flag_australia),
                    CurrencyOption("CAD", R.drawable.flag_canada),
                    CurrencyOption("MXN", R.drawable.flag_mexico),
                    CurrencyOption("PLN", R.drawable.flag_poland),
                    CurrencyOption("JPY", R.drawable.flag_japan)
                )
                currencies.forEach { currency ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCurrencySelected(currency) }
                            .padding(vertical = 8.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = currency.flagResId),
                            contentDescription = currency.code,
                            modifier = Modifier.size(width = 25.dp, height = 15.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = currency.code,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF00008B)
                        )
                    }
                }
            }
        }
    }
}
