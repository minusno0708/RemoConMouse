package com.example.smartmouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
    }

    private fun toMouse() {
        val isConnected: Boolean = serverManager.connect(binding.inputIp.text.toString(), 11000)
        if (isConnected) {
            val intent = Intent(this, MouseActivity::class.java)
            startActivity(intent)
        } else {
            val failedMessage = "接続に失敗しました"
            Toast.makeText(applicationContext, failedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
