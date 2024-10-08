package com.example.calculationing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.view.KeyEvent
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val editTextBinaryConversion = findViewById<EditText>(R.id.binaryConversion)
        val editTextBinaryConversion2 = findViewById<EditText>(R.id.binaryConversion2)
        val editTextBinaryConversion3 = findViewById<EditText>(R.id.binaryConversion3)
        val editTextBinaryConversion4 = findViewById<EditText>(R.id.binaryConversion4)
        val buttonConv = findViewById<Button>(R.id.buttonConvert)
        val textBinaryValue = findViewById<TextView>(R.id.binaryValue)

        // Set up listeners for each EditText
        setEditorActionListener(editTextBinaryConversion, editTextBinaryConversion2)
        setEditorActionListener(editTextBinaryConversion2, editTextBinaryConversion3)
        setEditorActionListener(editTextBinaryConversion3, editTextBinaryConversion4)

        buttonConv.setOnClickListener {
            val binaryValue = editTextBinaryConversion.text.toString().ifEmpty { "0" }
            val binaryValue2 = editTextBinaryConversion2.text.toString().ifEmpty { "0" }
            val binaryValue3 = editTextBinaryConversion3.text.toString().ifEmpty { "0" }
            val binaryValue4 = editTextBinaryConversion4.text.toString().ifEmpty { "0" }

            val address1 = binaryValue.toIntOrNull()
            val address2 = binaryValue2.toIntOrNull()
            val address3 = binaryValue3.toIntOrNull()
            val address4 = binaryValue4.toIntOrNull()

            if (validate(address1) &&
                validate(address2) &&
                validate(address3) &&
                validate(address4)
            ) {
                val toBeTexted = ipConvertBinary(
                    binaryValue,
                    binaryValue2,
                    binaryValue3,
                    binaryValue4
                )
                val netClass = addressClass(address1)
                textBinaryValue.text = toBeTexted +
                        "\n Subnet Mask: .0" +
                        "\n Host Address: $address1.$address2.$address3.$address4" +
                        "\n Broadcast Address: $address1.$address2.$address3.255 " +
                        "\n Network Class: $netClass" +
                        "\n Network Address: $address1.$address2.$address3.0"
            } else {
                textBinaryValue.text = "Value can't be higher than 255"
            }

        }
    }
}

fun validate(value: Int?): Boolean {
    return value != null && value in 0..255
}

fun ipConvertBinary (add1: String, add2: String, add3: String, add4: String): String {

    val addres1 = add1.toIntOrNull() ?: 0
    val addres2 = add2.toIntOrNull() ?: 0
    val addres3 = add3.toIntOrNull() ?: 0
    val addres4 = add4.toIntOrNull() ?: 0

    val binaryAddress = Integer.toBinaryString(addres1).padStart(8, '0')
    val binaryAddress2 = Integer.toBinaryString(addres2).padStart(8, '0')
    val binaryAddress3 = Integer.toBinaryString(addres3).padStart(8, '0')
    val binaryAddress4 = Integer.toBinaryString(addres4).padStart(8, '0')

    return "$binaryAddress.$binaryAddress2.$binaryAddress3.$binaryAddress4"
}
fun setEditorActionListener(currentEditText: EditText, nextEditText: EditText) {
    currentEditText.setOnEditorActionListener { _, actionId, event ->
        // Check if the action is the "Next" action or if Enter key was pressed
        if (actionId == EditorInfo.IME_ACTION_NEXT ||
            (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
        ) {
            nextEditText.requestFocus() // Move focus to the next EditText
            true
        } else {
            false
        }
    }
}

fun addressClass(ip: Int?): String {
    var addclass = ""
    if (ip != null) {
        addclass = when (ip) {
            in 0..127 -> {
                "A"
            }
            in 128..191 -> {
                "B"
            }
            in 192..223 -> {
                "C"
            }
            in 224..239 -> {
                "D"
            }
            in 240..255 -> {
                "E"
            }
            else -> {
                "unclassified"
            }
        }
    }
    return addclass
}


