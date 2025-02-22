package com.example.remoconmouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.remoconmouse.ui.screens.MainScreen

import androidx.activity.compose.setContent

object ServerData {
    val serverManager = ServerManager()
}

class MainActivity : ComponentActivity() {
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
