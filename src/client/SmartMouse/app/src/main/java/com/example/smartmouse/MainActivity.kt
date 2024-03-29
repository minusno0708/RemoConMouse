package com.example.smartmouse

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import com.example.smartmouse.databinding.ActivityMainBinding

object ServerData {
    val serverManager = ServerManager()
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val serverManager = ServerData.serverManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.toMouse.setOnClickListener {
            toMouse()
        }

        serverManager.connect("192.168.11.64", 11000)
        updateLog(serverManager.get())
    }

    private fun toMouse() {
        val intent = Intent(this, MouseActivity::class.java)
        startActivity(intent)
    }

    private fun updateLog(message: String) {
        binding.logMessage.text = message;
    }
}