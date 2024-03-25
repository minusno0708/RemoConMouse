package com.example.smartmouse

import android.content.Context
import android.os.Bundle
import android.hardware.SensorManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartmouse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscopeManager: GyroscopeManager
    private lateinit var gyroscopeListener: GyroscopeListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyroscopeListener = GyroscopeListener()
        gyroscopeManager = GyroscopeManager(sensorManager, gyroscopeListener)
    }

    inner class GyroscopeListener: GyroscopeManager.GyroscopeListener {
        override fun onValueChanged(x: Float, y: Float, z: Float) {
            updateValues(x, y, z)
        }
    }

    private fun updateValues(x: Float, y: Float, z: Float) {
        binding.gyroX.text = valueRound(x).toString()
        binding.gyroY.text = valueRound(y).toString()
        binding.gyroZ.text = valueRound(z).toString()
    }

    private fun valueRound(value: Float): Float {
        return Math.round(value * 100f) / 100f
    }

    override fun onResume() {
        super.onResume()
        gyroscopeManager.registerListener()
    }

    override fun onPause() {
        super.onPause()
        gyroscopeManager.unregisterListener()
    }
}