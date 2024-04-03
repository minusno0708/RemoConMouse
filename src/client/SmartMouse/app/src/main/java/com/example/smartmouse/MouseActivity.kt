package com.example.smartmouse

import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
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
        var clickCounter: Int = 0
        binding.leftClick.setOnClickListener {
            clickCounter++
            Handler(Looper.getMainLooper()).postDelayed({
                if (clickCounter == 1) {
                    mouseClick("left")
                } else if (clickCounter > 1) {
                    mouseClick("left-double")
                }
                clickCounter = 0
            }, 300)
        }
        binding.rightClick.setOnClickListener {
            clickCounter++
            Handler(Looper.getMainLooper()).postDelayed({
                if (clickCounter == 1) {
                    mouseClick("right")
                } else if (clickCounter > 1) {
                    mouseClick("right-double")
                }
                clickCounter = 0
            }, 300)
        }
        binding.middleClick.setOnClickListener {
            mouseClick("middle")
        }
        binding.scrollUp.setOnClickListener {
            mouseScroll("up")
        }
        binding.scrollDown.setOnClickListener {
            mouseScroll("down")
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        gyroscopeListener = GyroscopeListener()
        gyroscopeManager = GyroscopeManager(sensorManager, gyroscopeListener)

        accelerometerListener = AccelerometerListener()
        accelerometerManager = AccelerometerManager(sensorManager, accelerometerListener)

        updateLog(serverManager.get())
    }

    private fun toHome() {
        if (isMouseOn) {
            mouseDisable()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun switchMouseOnOf() {
        isMouseOn = if (!isMouseOn) {
            mouseEnable()
            true
        } else {
            mouseDisable()
            false
        }
    }

    private fun mouseEnable() {
        timer = Timer()
        timer.schedule(0, 100) {
            val moveX: Int = (-gyroValues[2]*100).toInt()
            val moveY: Int = (-gyroValues[0]*100).toInt()

            Thread {
                serverManager.sendUdp("move,${moveX},${moveY}")
            }.start()
        }
    }

    private fun mouseDisable() {
        timer.cancel()
    }

    private fun mouseClick(type: String) {
        val command = when (type) {
            "left" -> "click,left"
            "right" -> "click,right"
            "left-double" -> "click,left-double"
            "right-double" -> "click,right-double"
            "middle" -> "click,middle"
            else -> ""
        }

        Thread {
            serverManager.sendTcp(command)
        }.start()
    }

    private fun mouseScroll(type: String) {
        val command = when (type) {
            "up" -> "scroll,up"
            "down" -> "scroll,down"
            else -> ""
        }

        Thread {
            serverManager.sendUdp(command)
        }.start()
    }

    private fun updateLog(message: String) {
        binding.logMessage.text = message
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
}