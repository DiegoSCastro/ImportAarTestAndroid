package com.diegocastro.importaartestandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.diegocastro.importaartestandroid.ui.theme.ImportAarTestAndroidTheme
import com.mercadolibre.android.point_integration_sdk.nativesdk.MPManager
import com.mercadolibre.android.point_integration_sdk.nativesdk.configurable.MPConfigBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImportAarTestAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        onInitializeMPManager = { initializeMPManager() }
                    )
                }
            }
        }
    }
    private fun initializeMPManager() {
        val application = applicationContext as android.app.Application
        val config = MPConfigBuilder(application, "193213529837179")
            .withBluetoothConfig()
            .withBluetoothUIConfig()
            .build()
        MPManager.initialize(application, config)
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onInitializeMPManager: () -> Unit
) {
    Button(onClick = onInitializeMPManager) {
        Text(text = "Initialize MPManager")
    }
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImportAarTestAndroidTheme {
        Greeting("Android", onInitializeMPManager = {} )
    }
}