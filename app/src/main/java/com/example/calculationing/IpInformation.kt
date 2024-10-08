package com.example.calculationing

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IpInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ip_information)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.IpInformation)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextIPAddress = findViewById<EditText>(R.id.ipAddressInput)
        val buttonCalculate = findViewById<Button>(R.id.calculateButton)
        val textViewResult = findViewById<TextView>(R.id.testView)

        buttonCalculate.setOnClickListener {
            val ipAddress = editTextIPAddress.toString().replace(".", "")

            if (ipAddress != null) {
                textViewResult.text = ipAddress/*" Host Address: ${ipAddress[0]}.${ipAddress[1]}.${ipAddress[2]}.${ipAddress[3]}" +
                        "\nSubnet Mask: ${ipAddress[4]}.${ipAddress[5]}.${ipAddress[6]}.${ipAddress[7]}" +
                        "\nBroadcast Address: ${ipAddress[0]}.${ipAddress[1]}.${ipAddress[2]}.${ipAddress[9]}" +
                        "\nNetwork Class: ${ipAddress[8]}" +
                        "\nNetwork Address: ${ipAddress[0]}.${ipAddress[1]}.${ipAddress[2]}.${ipAddress[10]} "
                        */
            }else {
                textViewResult.text = "Error: Input not divisible by 4"
            }
        }
    }
}


