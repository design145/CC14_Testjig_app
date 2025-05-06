package com.example.basic_1

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import com.example.basic_1.ui.theme.Basic_1Theme
import com.example.basic_1.ui.theme.Basic_1Theme

class MainActivity : ComponentActivity() {
    private lateinit var bleManager: BleManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bleManager = BleManager(this)

        requestPermissions.launch(arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION
        ))

        setContent {
            Basic_1Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    KeyLedTestScreen(bleManager)
                }
            }
        }
    }

    private val requestPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }
}