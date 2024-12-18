package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    UnitConverter(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

val textStyle = TextStyle(
    fontFamily = FontFamily.Monospace,
    fontSize = 32.sp,
    color = Color.Cyan
)

val textStyleResult = TextStyle(
    fontFamily = FontFamily.Monospace,
    fontSize = 24.sp,
    color = Color.Green
)

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    var conversionFactor by remember { mutableDoubleStateOf(0.01) }
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("Select Output Type") }
    var inputUnit by remember { mutableStateOf("Inches") }
    var outputUnit by remember { mutableStateOf("Centimeters") }
    var isInputExpanded by remember { mutableStateOf(false) }
    var isOutputExpanded by remember { mutableStateOf(false) }

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = inputValueDouble * conversionFactor
        outputValue = String.format(Locale.US, "%.3f", result)
    }


    fun updateConversionFactor(from: String, to: String) {
        conversionFactor = when {
            from == "Feet" && to == "Meters" -> 0.3048
            from == "Feet" && to == "Centimeters" -> 30.48
            from == "Feet" && to == "Inches" -> 12.0
            from == "Meters" && to == "Feet" -> 3.28084
            from == "Meters" && to == "Centimeters" -> 100.0
            from == "Meters" && to == "Inches" -> 39.3701
            from == "Centimeters" && to == "Feet" -> 0.0328084
            from == "Centimeters" && to == "Meters" -> 0.01
            from == "Centimeters" && to == "Inches" -> 0.393701
            from == "Inches" && to == "Feet" -> 0.0833333
            from == "Inches" && to == "Meters" -> 0.0254
            from == "Inches" && to == "Centimeters" -> 2.54
            else -> 1.0
        }

        convertUnits()
    }

    LaunchedEffect(inputUnit, outputUnit) {
        updateConversionFactor(inputUnit, outputUnit)
    }

    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Unit Converter",
            style = textStyle
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = { Text("Enter Value") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Box {
                Button(onClick = { isInputExpanded = true }) {
                    Text(inputUnit)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Select unit type (Arrow Down)"
                    )
                }
                DropdownMenu(
                    expanded = isInputExpanded,
                    onDismissRequest = { isInputExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            isInputExpanded = false
                            inputUnit = "Feet"
                            updateConversionFactor(inputUnit, outputUnit)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            isInputExpanded = false
                            inputUnit = "Meters"
                            updateConversionFactor(inputUnit, outputUnit)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            isInputExpanded = false
                            inputUnit = "Centimeters"
                            conversionFactor = 0.01
                            updateConversionFactor(inputUnit, outputUnit)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Inches") },
                        onClick = {
                            isInputExpanded = false
                            inputUnit = "Inches"
                            updateConversionFactor(inputUnit, outputUnit)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { isOutputExpanded = true }) {
                    Text(outputUnit)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Select unit type (Arrow Down)"
                    )
                }
                DropdownMenu(
                    expanded = isOutputExpanded,
                    onDismissRequest = { isOutputExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            isOutputExpanded = false
                            outputUnit = "Feet"
                            updateConversionFactor(inputUnit, outputUnit)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            isOutputExpanded = false
                            outputUnit = "Meters"
                            updateConversionFactor(inputUnit, outputUnit)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            isOutputExpanded = false
                            outputUnit = "Centimeters"
                            updateConversionFactor(inputUnit, outputUnit)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Inches") },
                        onClick = {
                            isOutputExpanded = false
                            outputUnit = "Inches"
                            updateConversionFactor(inputUnit, outputUnit)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Result: $outputValue",
            style = textStyleResult
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}
