package com.diegocastro.importaartestandroid

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diegocastro.importaartestandroid.ui.theme.ImportAarTestAndroidTheme
import com.mercadolibre.android.point_integration_sdk.nativesdk.MPManager
import com.mercadolibre.android.point_integration_sdk.nativesdk.configurable.MPConfigBuilder
import com.mercadolibre.android.point_integration_sdk.nativesdk.message.utils.doIfError
import com.mercadolibre.android.point_integration_sdk.nativesdk.message.utils.doIfSuccess

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
                        onInitializeMPManager = { initializeMPManager() },
                        onPrint = { printImageBitmap() }
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
    private fun printImageBitmap() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable
            .point_mainapp_demo_app_ic_datafono)

        MPManager.bitmapPrinter.print(bitmap) { response ->
            response
                .doIfSuccess { printResult ->
                    Log.d("MiniAppPlugin", "Bitmap printed successfully: $printResult")
//                    result.success("Bitmap printed successfully: $printResult")
                }
                .doIfError { error ->
                    Log.e("MiniAppPlugin", "Failed to print bitmap: ${error.message}")
//                    result.error("PRINT_ERROR", "Failed to print bitmap: ${error.message}", null)
                }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onInitializeMPManager: () -> Unit,
    onPrint: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Button(onClick = onInitializeMPManager, modifier = Modifier.padding(16.dp)) {
            Text(text = "Initialize MPManager")
        }
        Button(onClick = onPrint, modifier = Modifier.padding(16.dp)) {
            Text(text = "Print Image Bitmap")
        }
        Text(
            text = "Hello $name!",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImportAarTestAndroidTheme {
        Greeting("Android", onInitializeMPManager = {}, onPrint = {})
    }
}