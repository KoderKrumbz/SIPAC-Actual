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
                ipDivider(ipAddress)
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

fun ipDivider(ip: String): List<String>? {
    if (ip.length % 3 != 0) {
        // Show an error message if input length is not divisible by 4
        return null
    } else {
        // Divide input into 4 equal parts
        val chunkSize = ip.length / 4
        val part1 = ip.substring(0, chunkSize)
        val part2 = ip.substring(chunkSize, chunkSize * 2)
        val part3 = ip.substring(chunkSize * 2, chunkSize * 3)
        val part4 = ip.substring(chunkSize * 3)

        var subnetMaskV1 = "0"
        var subnetMaskV2 = "0"
        var subnetMaskV3 = "0"
        val subnetMaskV4 = "0"

        val fNC = part1.toInt()

        if (part1.toInt() > 0) {
            subnetMaskV1 = "255"
        }
        if (part2.toInt()> 0) {
            subnetMaskV2 = "255"
        }
        if (part3.toInt() > 0) {
            subnetMaskV3 = "255"
        }

        val networkClass: String = when (fNC) {
            in 1..127 -> {
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
            else -> {
                "E"
            }
        }
        val broadcastAddress = "255"
        val networkAddress = "0"

        return listOf(part1, part2, part3, part4,subnetMaskV1,subnetMaskV2,subnetMaskV3,subnetMaskV4,networkClass,broadcastAddress,networkAddress)
    }
}
