package com.example.simplecomposeproject.RateCoverter.ActivityLayouts

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplecomposeproject.OfflineRateHistoryActivity
import com.example.simplecomposeproject.R
import com.example.simplecomposeproject.RateCoverter.fxdates.viewmodel.RateViewModel
import com.example.simplecomposeproject.RateCoverter.DialogLayouts.SelectCurrencyToDialog
import com.example.simplecomposeproject.RateCoverter.offlinehistory.RatesModel
import com.example.simplecomposeproject.RateCoverter.offlinehistory.viewmodel.RatesModelViewModel
import com.example.simplecomposeproject.RateCoverter.DialogLayouts.SelectCurrencyFromDialog
import com.example.simplecomposeproject.RateCoverter.DialogLayouts.CustomProgressDialog
import com.example.simplecomposeproject.theme.SImpleComposeProjectTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Extension operator for Rates to retrieve a specific currency rate by its code.
private operator fun com.example.simplecomposeproject.RateCoverter.fxdates.api.Rates.get(currency: String): Double? {
    return when (currency) {
        "AUD" -> this.AUD
        "CAD" -> this.CAD
        "MXN" -> this.MXN
        "PLN" -> this.PLN
        "EUR" -> this.EUR
        "JPY" -> this.JPY
        "USD" -> this.USD
        "GBP" -> this.GBP
        else -> null
    }
}

@Composable
fun CurrencyCalculatorScreen(
    viewModel: RateViewModel = hiltViewModel(),
    offlineRateViewModel: RatesModelViewModel = hiltViewModel() // For saving valid rates
) {
    // Observe API response and error.
    val rates by viewModel.rates.observeAsState()
    val loading by viewModel.loading.observeAsState(initial = false)
    val errorState by viewModel.error.observeAsState()

    // Local state for input and conversion result.
    var amount by remember { mutableStateOf("1") }
    var result by remember { mutableStateOf("...") }

    // Currency selection.
    var baseCurrency by remember { mutableStateOf("EUR") }
    var fromCurrency by remember { mutableStateOf("EUR") }
    var toCurrency by remember { mutableStateOf("USD") }

    // Flag resources.
    var fromFlagRes by remember { mutableStateOf(com.hbb20.R.drawable.flag_netherlands) }
    var toFlagRes by remember { mutableStateOf(com.hbb20.R.drawable.flag_united_states_of_america) }

    // Dialog visibility.
    var showFromCurrencyDialog by remember { mutableStateOf(false) }
    var showToCurrencyDialog by remember { mutableStateOf(false) }

    // Compute conversion result.
    LaunchedEffect(rates, fromCurrency, toCurrency, amount) {
        rates?.let { moneyResponse ->
            val fromRate = moneyResponse.rates?.get(fromCurrency) ?: 1.0
            val toRate = moneyResponse.rates?.get(toCurrency) ?: 0.0
            amount.toDoubleOrNull()?.let { amt ->
                result = if (fromRate != 0.0) String.format(
                    "%.5f",
                    amt * (toRate / fromRate)
                ) else "0.00"
            }
        }
    }

    // Automatically Fetch rates when the screen is composed.
    /*
        LaunchedEffect(Unit) {
            viewModel.setError("")
            viewModel.fetchRates(baseCurrency)
        }
    */

    // Save conversion if valid; if result equals "0.00000", set a specific error.
    LaunchedEffect(result, errorState) {
        if ((errorState.isNullOrBlank()) && result != "Loading..." && result != "..." && result != "0.00" && result != "0.00000" && amount.toDoubleOrNull() != null) {
            saveValidRates(fromCurrency, toCurrency, result, offlineRateViewModel)
        }
    }


    // Compute the error message to display.
    val computedErrorMessage = remember(errorState, result) {
        when {
            !errorState.isNullOrBlank() -> errorState!!
            else -> ""
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            val (
                imageView15, textView16, textView2, textDisplayErrorMessage,
                editTextEnterAmount, textDisplayFrom, textResult, textDisplayTo,
                imageView13, linearLayoutSelectFrom, linearLayoutSelectTo,
                btnRequestRate, textViewAllPastRates
            ) = createRefs()

            // "Sort" icon.
            Image(
                painter = painterResource(id = R.drawable.ic_nav_sort_24),
                contentDescription = null,
                modifier = Modifier.constrainAs(imageView15) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                }
            )

            // "Sign up" text.
            Text(
                text = "Sign up",
                fontSize = 16.sp,
                color = Color(0xFF006400),
                modifier = Modifier.constrainAs(textView16) {
                    top.linkTo(parent.top, margin = 16.dp)
                    end.linkTo(parent.end)
                }
            )

            // Title text.
            Text(
                text = "Currency\nCalculator.",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00008B),
                lineHeight = 38.sp,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 3.sp,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .constrainAs(textView2) {
                        top.linkTo(imageView15.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                    }
            )

            // Display error message if there is one.
            if (computedErrorMessage.isNotBlank()) {
                Text(
                    text = computedErrorMessage,
                    fontSize = 14.sp,
                    color = Color.Red,
                    modifier = Modifier.constrainAs(textDisplayErrorMessage) {
                        top.linkTo(textView2.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }

            // Common modifier for input/result fields.
            val commonFieldModifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, shape = RectangleShape)
                .border(
                    width = 1.dp,
                    color = Color.Gray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(start = 15.dp)

            // Amount input field.
            Box(
                modifier = Modifier
                    .constrainAs(editTextEnterAmount) {
                        top.linkTo(
                            if (computedErrorMessage.isNotBlank()) textDisplayErrorMessage.bottom
                            else textView2.bottom, margin = 32.dp
                        )
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .then(commonFieldModifier),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                        }
                    }
                )
            }

            // Display selected "from" currency.
            Text(
                text = fromCurrency,
                fontSize = 16.sp,
                color = Color.LightGray,
                modifier = Modifier.constrainAs(textDisplayFrom) {
                    top.linkTo(editTextEnterAmount.top)
                    bottom.linkTo(editTextEnterAmount.bottom)
                    end.linkTo(editTextEnterAmount.end, margin = 16.dp)
                }
            )

            // Result display field.
            Box(
                modifier = Modifier
                    .constrainAs(textResult) {
                        top.linkTo(editTextEnterAmount.bottom, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .then(commonFieldModifier),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = if (loading) "Loading..." else result,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
            }

            // Display selected "to" currency.
            Text(
                text = toCurrency,
                fontSize = 16.sp,
                color = Color.LightGray,
                modifier = Modifier.constrainAs(textDisplayTo) {
                    top.linkTo(textResult.top)
                    bottom.linkTo(textResult.bottom)
                    end.linkTo(textResult.end, margin = 16.dp)
                }
            )

            // Sync icon.
            Image(
                painter = painterResource(id = R.drawable.ic_outline_sync_alt_24),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .constrainAs(imageView13) {
                        top.linkTo(textResult.bottom, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                colorFilter = ColorFilter.tint(Color.LightGray)
            )

            // "From" currency selection layout.
            ConstraintLayout(
                modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp))
                    .padding(10.dp)
                    .constrainAs(linearLayoutSelectFrom) {
                        top.linkTo(imageView13.top)
                        bottom.linkTo(imageView13.bottom)
                        start.linkTo(parent.start, margin = 18.dp)
                        end.linkTo(imageView13.start, margin = 32.dp)
                    }
                    .clickable { showFromCurrencyDialog = true }
            ) {
                val (fromImage, fromText, fromDropdown) = createRefs()
                Image(
                    painter = painterResource(id = fromFlagRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 25.dp, height = 18.dp)
                        .constrainAs(fromImage) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = fromCurrency,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(fromText) {
                        start.linkTo(fromImage.end, margin = 10.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.ccp_ic_arrow_drop_down),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(2.dp)
                        .constrainAs(fromDropdown) {
                            start.linkTo(fromText.end, margin = 10.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }

            // "To" currency selection layout.
            ConstraintLayout(
                modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp))
                    .padding(10.dp)
                    .constrainAs(linearLayoutSelectTo) {
                        top.linkTo(imageView13.top)
                        bottom.linkTo(imageView13.bottom)
                        start.linkTo(imageView13.end, margin = 32.dp)
                        end.linkTo(parent.end, margin = 18.dp)
                    }
                    .clickable { showToCurrencyDialog = true }
            ) {
                val (toImage, toText, toDropdown) = createRefs()
                Image(
                    painter = painterResource(id = toFlagRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 25.dp, height = 18.dp)
                        .constrainAs(toImage) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = toCurrency,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(toText) {
                        start.linkTo(toImage.end, margin = 10.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.ccp_ic_arrow_drop_down),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(2.dp)
                        .constrainAs(toDropdown) {
                            start.linkTo(toText.end, margin = 10.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }

            // "Convert" button.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(Color(0xFF4CAF50), shape = RoundedCornerShape(4.dp))
                    .clickable {
                        // Clear error before fetching rates.
                        viewModel.setError("")
                        viewModel.fetchRates("EUR")
                    }
                    .constrainAs(btnRequestRate) {
                        top.linkTo(imageView13.bottom, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Convert",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Footer text.
            val ctx = LocalContext.current
            Text(
                text = "Click to view all past rates",
                fontSize = 14.sp,
                color = Color(0xFF858885),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(textViewAllPastRates) {
                        top.linkTo(btnRequestRate.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 32.dp)
                    }
                    .clickable {
                        val intent = Intent(ctx, OfflineRateHistoryActivity::class.java)
                        intent.putExtra("result", result)
                        ctx.startActivity(intent)
                        val activity = ctx as? Activity
                        activity?.finish() // finish current activity
                    }
            )
        }

        if (loading) {
            CustomProgressDialog()
        }
    }

    // Show dialogs if needed.
    if (showFromCurrencyDialog) {
        SelectCurrencyFromDialog(
            onCurrencySelected = { selectedOption ->
                fromCurrency = selectedOption.code
                fromFlagRes = selectedOption.flagResId
                baseCurrency = selectedOption.code // Base remains EUR for fetching rates.
                showFromCurrencyDialog = false
            },
            onDismissRequest = { showFromCurrencyDialog = false }
        )
    }
    if (showToCurrencyDialog) {
        SelectCurrencyToDialog(
            onCurrencySelected = { selectedOption ->
                toCurrency = selectedOption.code
                toFlagRes = selectedOption.flagResId
                showToCurrencyDialog = false
            },
            onDismissRequest = { showToCurrencyDialog = false }
        )
    }
}

private suspend fun saveValidRates(
    from: String,
    to: String,
    rate: String,
    offlineRateViewModel: RatesModelViewModel
) {
    val dateFormat = SimpleDateFormat("MM-dd-yyyy, hh:mm a", Locale.getDefault())
    val currentTime = dateFormat.format(Date())
    val user = RatesModel(from = from, to = to, rate = rate, time = currentTime)
    withContext(Dispatchers.IO) {
        offlineRateViewModel.addRates(user)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCurrencyCalculator() {
    SImpleComposeProjectTheme {
        CurrencyCalculatorScreen()
    }
}
