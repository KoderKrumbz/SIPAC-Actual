package com.example.calculationing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")

    private lateinit var viewContainer: FrameLayout
    private lateinit var viewSpinner: Spinner

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
                textBinaryValue.text = toBeTexted
            } else {
                textBinaryValue.text = "Value can't be higher than 255"
            }

        }

        viewSpinner = findViewById(R.id.viewSwitcher)
        viewContainer = findViewById(R.id.pageOne)

        // Setting up the Spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.view_options, // Define this in res/values/strings.xml
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewSpinner.adapter = adapter

        // Load the initial view
        loadView(R.layout.activity_main)


        viewSpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
             override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> loadView(R.layout.activity_main) // First item selected
                    1 -> loadView(R.layout.activity_ip_information) // Second item selected
                    2 -> loadView(R.layout.activity_subnet_masking) // Third item selected
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }
    fun loadView(layoutId: Int) {
        // Remove all existing views and load the selected layout
        viewContainer.removeAllViews()
        val inflater = LayoutInflater.from(this)
        inflater.inflate(layoutId, viewContainer, true)
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
