package ppb.iftala.moneyexchanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyConverterScreen()
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterScreen() {
    val usdToIdrRate = 15750.0

    var usdAmount by remember { mutableStateOf("") }
    var idrAmount by remember { mutableStateOf("") }
    var lastEditedField by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Currency Converter",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // USD Input Field
        OutlinedTextField(
            value = usdAmount,
            onValueChange = { newValue ->
                usdAmount = newValue
                lastEditedField = "USD"
                if (newValue.isNotEmpty()) {
                    try {
                        val usd = newValue.toDouble()
                        idrAmount = (usd * usdToIdrRate).toInt().toString()
                    } catch (e: NumberFormatException) {
                        idrAmount = ""
                    }
                } else {
                    idrAmount = ""
                }
            },
            label = { Text("USD Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = idrAmount,
            onValueChange = { newValue ->
                idrAmount = newValue
                lastEditedField = "IDR"
                if (newValue.isNotEmpty()) {
                    try {
                        val idr = newValue.toDouble()
                        usdAmount = (idr / usdToIdrRate).toString()
                    } catch (e: NumberFormatException) {
                        usdAmount = ""
                    }
                } else {
                    usdAmount = ""
                }
            },
            label = { Text("IDR Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Current Exchange Rate Info
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Current Exchange Rate",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "1 USD = ${usdToIdrRate.toInt()} IDR")
                Text(text = "1 IDR = ${(1 / usdToIdrRate).toFloat()} USD")
            }
        }

        // Reset Button
        Button(
            onClick = {
                usdAmount = ""
                idrAmount = ""
                lastEditedField = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Reset")
        }
    }
}