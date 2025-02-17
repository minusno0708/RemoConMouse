package com.example.remoconmouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.remoconmouse.databinding.ActivityMainBinding

import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ServerData {
    val serverManager = ServerManager()
}

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    private val serverManager = ServerData.serverManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen { ip -> toMouse(ip) }
        }
    }

    private fun toMouse(ip: String) {
        val isConnected: Boolean = if (isIP(ip)) serverManager.connect(ip, 11000) else false

        if (isConnected) {
            val intent = Intent(this, MouseActivity::class.java)
            startActivity(intent)
        } else {
            val failedMessage = "接続に失敗しました"
            Toast.makeText(applicationContext, failedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isIP(ip: String) :Boolean {
        if (ip.isBlank()) return false

        val parsedIP = ip.split(".")

        if (parsedIP.size != 4) return false

        return parsedIP.all { section ->
            section.toIntOrNull()?.let { it in 0..255 } ?: false
        }
    }
}

@Composable
fun MainScreen(onConnect: (String) -> Unit) {
    var ipAddress by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "RemoConMouse",
            fontSize = 40.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = ipAddress,
            onValueChange = { ipAddress = it },
            label = { Text("接続先のIPを入力してください") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onConnect(ipAddress) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text("Connect", fontSize = 20.sp)
        }
    }
}