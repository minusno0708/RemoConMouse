package com.example.smartmouse

import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartmouse.databinding.ActivityMouseBinding
import java.util.Timer
import kotlin.concurrent.schedule

class MouseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMouseBinding

    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscopeManager: GyroscopeManager
    private lateinit var gyroscopeListener: MouseActivity.GyroscopeListener
    private lateinit var accelerometerManager: AccelerometerManager
    private lateinit var accelerometerListener: MouseActivity.AccelerometerListener

    private val serverManager = ServerData.serverManager
    private var timer = Timer()

    private var gyroValues: FloatArray = floatArrayOf(0f, 0f, 0f)
    private var accValues: FloatArray = floatArrayOf(0f, 0f, 0f)

    private var isMouseOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMouseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.toHome.setOnClickListener {
            toHome()
        }
        binding.mouseSwitch.setOnClickListener {
            switchMouseOnOf()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        gyroscopeListener = GyroscopeListener()
        gyroscopeManager = GyroscopeManager(sensorManager, gyroscopeListener)

        accelerometerListener = AccelerometerListener()
        accelerometerManager = AccelerometerManager(sensorManager, accelerometerListener)

        updateLog(serverManager.get())
    }

    private fun toHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun switchMouseOnOf() {
        if (!isMouseOn) {
            mouseEnable()
            isMouseOn = true
        } else {
            mouseDisable()
            isMouseOn = false
        }
    }

    private fun updateLog(message: String) {
        binding.logMessage.text = message;
    }

    inner class GyroscopeListener: GyroscopeManager.GyroscopeListener {
        override fun onValueChanged(x: Float, y: Float, z: Float) {
            gyroValues = floatArrayOf(x, y, z)
        }
    }

    inner class AccelerometerListener: AccelerometerManager.AccelerometerListener {
        override fun onValueChanged(x: Float, y: Float, z: Float) {
            accValues = floatArrayOf(x, y, z)
        }
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

    private fun mouseEnable() {
        timer = Timer()
        timer.schedule(0, 100) {
            val moveX: Int = (-gyroValues[2]*100).toInt()
            val moveY: Int = (-gyroValues[0]*100).toInt()

            Thread {
                serverManager.send("move.${moveX}.${moveY}".toByteArray())
            }.start()
        }
    }

    private fun mouseDisable() {
        timer.cancel()
    }

}