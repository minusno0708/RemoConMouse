package com.example.smartmouse

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import com.example.smartmouse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager

    private lateinit var gyroscopeManager: GyroscopeManager
    private lateinit var gyroscopeListener: GyroscopeListener

    private lateinit var accelerometerManager: AccelerometerManager
    private lateinit var accelerometerListener: AccelerometerListener

    private val serverManager = ServerManager()
    private val controller = OutputController(serverManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.toMouse.setOnClickListener {
            toMouse()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        gyroscopeListener = GyroscopeListener()
        gyroscopeManager = GyroscopeManager(sensorManager, gyroscopeListener)

        accelerometerListener = AccelerometerListener()
        accelerometerManager = AccelerometerManager(sensorManager, accelerometerListener)

        serverManager.connect("192.168.11.64", 11000)
        controller.mouseEnable()
    }

    private fun toMouse() {
        val intent = Intent(this, MouseActivity::class.java)
        startActivity(intent)
    }

    inner class GyroscopeListener: GyroscopeManager.GyroscopeListener {
        override fun onValueChanged(x: Float, y: Float, z: Float) {
            updateGyroValues(x, y, z)
        }
    }

    private fun updateGyroValues(x: Float, y: Float, z: Float) {
        controller.setMoveCoo(
            (-z*100).toInt(),
            (-x*100).toInt()
        )
    }

    inner class AccelerometerListener: AccelerometerManager.AccelerometerListener {
        override fun onValueChanged(x: Float, y: Float, z: Float) {
            updateAccValues(x, y, z)
        }
    }

    private fun updateAccValues(x: Float, y: Float, z: Float) {
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