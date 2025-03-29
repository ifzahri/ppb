package ppb.iftala.calculator

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
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var valueA by remember { mutableStateOf("") }
    var valueB by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Simple Calculator",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            TextField(
                value = valueA,
                onValueChange = { valueA = it },
                label = { Text("Value A") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = valueB,
                onValueChange = { valueB = it },
                label = { Text("Value B") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Result: $result",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AddButton(valueA, valueB) { result = it }
                SubButton(valueA, valueB) { result = it }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MulButton(valueA, valueB) { result = it }
                DivButton(valueA, valueB) { result = it }
            }
        }
    }
}

@Composable
fun AddButton(valueA: String, valueB: String, onResult: (String) -> Unit) {
    Button(
        onClick = {
            try {
                val a = valueA.toDouble()
                val b = valueB.toDouble()
                onResult((a + b).toString())
            } catch (e: NumberFormatException) {
                onResult("Invalid input")
            }
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "+")
    }
}

@Composable
fun SubButton(valueA: String, valueB: String, onResult: (String) -> Unit) {
    Button(
        onClick = {
            try {
                val a = valueA.toDouble()
                val b = valueB.toDouble()
                onResult((a - b).toString())
            } catch (e: NumberFormatException) {
                onResult("Invalid input")
            }
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "-")
    }
}

@Composable
fun MulButton(valueA: String, valueB: String, onResult: (String) -> Unit) {
    Button(
        onClick = {
            try {
                val a = valueA.toDouble()
                val b = valueB.toDouble()
                onResult((a * b).toString())
            } catch (e: NumberFormatException) {
                onResult("Invalid input")
            }
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "×")
    }
}

@Composable
fun DivButton(valueA: String, valueB: String, onResult: (String) -> Unit) {
    Button(
        onClick = {
            try {
                val a = valueA.toDouble()
                val b = valueB.toDouble()
                if (b == 0.0) {
                    onResult("Cannot divide by zero")
                } else {
                    onResult((a / b).toString())
                }
            } catch (e: NumberFormatException) {
                onResult("Invalid input")
            }
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "÷")
    }
}