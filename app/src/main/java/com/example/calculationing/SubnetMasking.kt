package com.example.calculationing

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SubnetMasking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_subnet_masking)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SubnetMask)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextIPAddress = findViewById<EditText>(R.id.ipAddressInput)
        val editTextPrefixLength = findViewById<EditText>(R.id.editTextPrefixLength)
        val buttonCalculate = findViewById<Button>(R.id.calculateButton)
        val textViewResult = findViewById<TextView>(R.id.resultText)

    }
}

fun getSubnetMask(prefixLength: Int): String {
    val subnetMask = (0xFFFFFFFF shl (32 - prefixLength)) and 0xFFFFFFFF.toUInt().toLong()
    return listOf(
        (subnetMask shr 24) and 0xFFL,
        (subnetMask shr 16) and 0xFFL,
        (subnetMask shr 8) and 0xFFL,
        subnetMask and 0xFFL
    ).joinToString(".")
}
