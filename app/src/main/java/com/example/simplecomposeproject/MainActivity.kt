package com.example.simplecomposeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplecomposeproject.RateCoverter.ActivityLayouts.CurrencyCalculatorScreen
import com.example.simplecomposeproject.theme.SImpleComposeProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SImpleComposeProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyCalculatorScreen() // Use your composable here
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyCalculatorPreview() {
    SImpleComposeProjectTheme {
        CurrencyCalculatorScreen()
    }
}
