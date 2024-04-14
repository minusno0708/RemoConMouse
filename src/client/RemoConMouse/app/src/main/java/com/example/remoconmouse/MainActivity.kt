package com.example.remoconmouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.remoconmouse.databinding.ActivityMainBinding

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
        val ip: String = binding.inputIp.text.toString()

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
        if (ip == "") {
            return false
        }

        val parsedIP = ip.split(".")

        if (parsedIP.size != 4) {
            return false
        }

        val isNumList: (List<String>) -> Boolean = { address ->
            for (section in address) {
                try {
                    section.toInt()
                } catch (_: Exception) {
                    false
                }
            }
            true
        }

        return if(isNumList(parsedIP)) (parsedIP[0] != "0") else false
    }
}
