package com.example.milkbillcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.milkbillcalculator.ui.screens.MainScreen
import com.example.milkbillcalculator.ui.MilkEntryViewModel
import com.example.milkbillcalculator.ui.theme.MilkbillCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MilkbillCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MilkEntryViewModel = viewModel()
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun PreviewMainActivity() {
    MilkbillCalculatorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val viewModel: MilkEntryViewModel = viewModel()
            MainScreen(viewModel = viewModel)
        }
    }
}