package com.example.smartmouse

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartmouse.databinding.ActivityMouseBinding

class MouseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMouseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMouseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.toHome.setOnClickListener {
            toHome()
        }
    }

    private fun toHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}