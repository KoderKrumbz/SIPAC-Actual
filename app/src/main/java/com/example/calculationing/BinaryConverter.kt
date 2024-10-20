package com.example.calculationing

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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
        val editTextPrefixLength = findViewById<EditText>(R.id.prefixLength)
        val buttonConv = findViewById<Button>(R.id.buttonConvert)
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        val textBinaryValue = findViewById<TextView>(R.id.binaryValue)
        val textBinbaryVal2 = findViewById<TextView>(R.id.NetworkDeets)

        textBinaryValue.movementMethod = ScrollingMovementMethod()
        textBinbaryVal2.movementMethod = ScrollingMovementMethod()

        // Set up listeners for each EditText
        setEditorActionListener(editTextBinaryConversion, editTextBinaryConversion2)
        setEditorActionListener(editTextBinaryConversion2, editTextBinaryConversion3)
        setEditorActionListener(editTextBinaryConversion3, editTextBinaryConversion4)

        buttonConv.setOnClickListener {
            val binaryValue = editTextBinaryConversion.text.toString().ifEmpty { "0" }
            val binaryValue2 = editTextBinaryConversion2.text.toString().ifEmpty { "0" }
            val binaryValue3 = editTextBinaryConversion3.text.toString().ifEmpty { "0" }
            val binaryValue4 = editTextBinaryConversion4.text.toString().ifEmpty { "0" }
            val prefLengths = editTextPrefixLength.text.toString().ifEmpty { "0" }

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
                val altpref = ifNoPrefLeng(prefLengths,netClass)
                val numNet = numNetworks(altpref)
                val hostnum = numHosts(altpref)
                val subMaskText = subnetMask(netClass)
                val hostAdd = hostNotBroad(binaryValue4)
                val wcM = wildcardMask(netClass)
                val maskBi = binaryMask(netClass)
                val wildBi = binaryWild(netClass)
                textBinaryValue.text =  "Subnet Mask: $subMaskText" +
                        "\nWildcard Mask: $wcM" +
                        "\nHost Address: $address1.$address2.$address3.$hostAdd" +
                        "\nFirst Host: $address1.$address2.$address3.1" +
                        "\nLast Host: $address1.$address2.$address3.254" +
                        "\nNetwork Address: $address1.$address2.$address3.0" +
                        "\nBroadcast Address: $address1.$address2.$address3.255" +
                        "\nNetwork Class: $netClass" +
                        "\nPrefix Length: $altpref" +
                        "\nNumber of Networks: $numNet" +
                        "\nNumber of Hosts: $hostnum"
                textBinbaryVal2.text =  "IP Binary Notation: $toBeTexted" +
                        "\nSubnet Mask: $maskBi" +
                        "\nWildcard Mask: $wildBi"

            } else {
                textBinaryValue.text = "Value can't be higher than 255"
            }
        }

        buttonClear.setOnClickListener {
            editTextPrefixLength.text.clear()
            editTextBinaryConversion.text.clear()
            editTextBinaryConversion2.text.clear()
            editTextBinaryConversion3.text.clear()
            editTextBinaryConversion4.text.clear()

            textBinbaryVal2.text = ""
            textBinaryValue.text = ""

        }
    }
}

//identifies whether given IP value is valid
fun validate(value: Int?): Boolean {
    return value != null && value in 0..255
}

//turns input ip Values into binary
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

//automatically changes input box when "Next" is clicked on the keyboard
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

//identifies address class
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

//identifies subnet mask using address class
fun subnetMask(netClass: String): String {
    return if (netClass.contains("A", ignoreCase = true)) {
        "255.000.000.000"
    } else if (netClass.contains("B", ignoreCase = true)) {
        "255.255.000.000"
    } else if (netClass.contains("C", ignoreCase = true)) {
        "255.255.255.000"
    } else {
        "Null"
    }
}

//identifies number of networks for the ip depending on the prefix length
fun numNetworks(prefLength: String): String {
    val pL = 32 - prefLength.toInt()
    val nOn = 2.0.pow(pL)
    val non = nOn.toInt()
    val yes = non.toString()
    return yes
}

//identifies number of hosts for the ip depending on the prefix length
fun numHosts(prefLength: String): String {
    val prefles = prefLength.toInt()
    val  nOh = 2.0.pow(prefles) - 2
    val noh = nOh.toInt()
    val huh = noh.toString()
    return huh
}

//automatically inputs a prefix length based on the address class if there is no given prefix length
fun ifNoPrefLeng(zeroe: String, netC: String): String {
    return if (zeroe == "") {
        when (netC) {
            "A" -> {
                "8"
            }
            "B" -> {
                "16"
            }
            else -> {
                "24"
            }
        }
    } else {
        zeroe
    }
}

//last usable ip if given ip is 255 what?
fun hostNotBroad(ip: String): String{
    return if (ip == "255") {
        "254"
    } else {
        ip
    }
}

//identifies wildcard mask
fun wildcardMask(subnet: String): String{
    //val sbnet = subnet.replace(".","")
    //return sbnet.map {
    //    if (it == '0') '1' else '0'
    //}.joinToString ("")
    return when (subnet) {
        "A" -> {
            "000.255.255.255"
        }
        "B" -> {
            "000.000.255.255"
        }
        "C" -> {
            "000.000.000.255"
        }
        else -> {
            "255.255.255.255"
        }
    }
}

//converts subnet mask into binary
fun binaryMask(classs: String): String{
    return when (classs) {
        "A" -> {
            "11111111.00000000.00000000.00000000"
        }
        "B" -> {
            "11111111.11111111.00000000.00000000"
        }
        "C" -> {
            "11111111.11111111.11111111.00000000"
        }
        else -> {
            "11111111.11111111.11111111.11111111"
        }

    }
}

//converts wildcard mask into binary
fun binaryWild(classs: String): String{
    return when (classs) {
        "A" -> {
            "00000000.11111111.11111111.11111111"
        }
        "B" -> {
            "00000000.00000000.11111111.11111111"
        }
        "C" -> {
            "00000000.00000000.00000000.11111111"
        }
        else -> {
            "00000000.00000000.00000000.00000000"
        }
    }
}