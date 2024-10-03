package com.example.calculationing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
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
            val binaryValu = editTextBinaryConversion.text.toString()
            val binaryValu2 = editTextBinaryConversion2.text.toString()
            val binaryValu3 = editTextBinaryConversion3.text.toString()
            val binaryValu4 = editTextBinaryConversion4.text.toString()
            val toBeBinary = binaryValu.toInt()
            val toBeText = Integer.toBinaryString(toBeBinary)
            val toBeTexted = ipConvertBinary(
                binaryValu,
                binaryValu2,
                binaryValu3,
                binaryValu4
                )

            if (binaryValu.isNotEmpty()) {
                if (toBeBinary < 256 && toBeBinary > -1) {
                    //Toast.makeText(this, "IP Address: $binaryValu", Toast.LENGTH_SHORT).show()
                    textBinaryValue.text = toBeTexted
                } else {
                    //Toast.makeText(this, "Please enter a valid value.", Toast.LENGTH_SHORT).show()
                    textBinaryValue.text = "Please Input a Valid Value"
                }
            } else {
                textBinaryValue.text = "Please Input a Valid Value"
            }
        }
    }
}

fun ipConvertBinary (add1: String = "0", add2: String = "0", add3: String = "0",add4: String ="0"): String {

    val addres1 = add1.toIntOrNull() ?: 0
    val addres2 = add2.toIntOrNull() ?: 0
    val addres3 = add3.toIntOrNull() ?: 0
    val addres4 = add4.toIntOrNull() ?: 0


    /*
    val binaryAddress = Integer.toBinaryString(addres1)
    val binaryAddress2 = Integer.toBinaryString(addres2)
    val binaryAddress3 = Integer.toBinaryString(addres3)
    val binaryAddress4 = Integer.toBinaryString(addres4)
    */

    return "$binaryAddress.$binaryAddress2.$binaryAddress3.$binaryAddress4"
}
fun setEditorActionListener(currentEditText: EditText, nextEditText: EditText) {
    currentEditText.setOnEditorActionListener { _, actionId, event ->
        // Check if the action is the "Next" action or if Enter key was pressed
        if (actionId == EditorInfo.IME_ACTION_NEXT ||
            (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
        ) {
            nextEditText.requestFocus() // Move focus to the next EditText
            true // Return true to indicate the action was handled
        } else {
            false // Return false if not handled
        }
    }
}