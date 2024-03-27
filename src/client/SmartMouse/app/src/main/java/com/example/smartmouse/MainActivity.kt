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

    private lateinit var accelerometerManager: AccelerometerManager
    private lateinit var accelerometerListener: AccelerometerListener

    private val connectClient = ConnectClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        gyroscopeListener = GyroscopeListener()
        gyroscopeManager = GyroscopeManager(sensorManager, gyroscopeListener)

        accelerometerListener = AccelerometerListener()
        accelerometerManager = AccelerometerManager(sensorManager, accelerometerListener)
    }

    inner class GyroscopeListener: GyroscopeManager.GyroscopeListener {
        override fun onValueChanged(x: Float, y: Float, z: Float) {
            connectClient.connect("192.168.11.64", 11000, "test".toByteArray())
            updateGyroValues(x, y, z)
        }
    }

    private fun updateGyroValues(x: Float, y: Float, z: Float) {
        binding.gyroX.text = valueRound(x).toString()
        binding.gyroY.text = valueRound(y).toString()
        binding.gyroZ.text = valueRound(z).toString()
    }

    inner class AccelerometerListener: AccelerometerManager.AccelerometerListener {
        override fun onValueChanged(x: Float, y: Float, z: Float) {
            updateAccValues(x, y, z)
        }
    }

    private fun updateAccValues(x: Float, y: Float, z: Float) {
        binding.accX.text = valueRound(x).toString()
        binding.accY.text = valueRound(y).toString()
        binding.accZ.text = valueRound(z).toString()
    }

    private fun valueRound(value: Float): Float {
        return Math.round(value * 100f) / 100f
    }

    override fun onResume() {
        super.onResume()
        gyroscopeManager.registerListener()
        accelerometerManager.registerListener()
    }

    override fun onPause() {
        super.onPause()
        gyroscopeManager.unregisterListener()
        accelerometerManager.unregisterListener()
    }

    private fun updateLog(message: String) {
        binding.logMessage.text = message;
    }
}