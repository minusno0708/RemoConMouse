package com.example.remoconmouse

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.remoconmouse.databinding.ActivityMouseBinding
import com.example.remoconmouse.ui.MainScreen
import java.util.Timer
import kotlin.concurrent.schedule

import com.example.remoconmouse.ui.MouseScreen

class MouseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMouseBinding

    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscopeManager: GyroscopeManager
    private lateinit var gyroscopeListener: MouseActivity.GyroscopeListener
    private lateinit var accelerometerManager: AccelerometerManager
    private lateinit var accelerometerListener: MouseActivity.AccelerometerListener

    private val serverManager = ServerData.serverManager
    private var moveTimer = Timer()
    private var scrollTimer = Timer()

    private var gyroValues: FloatArray = floatArrayOf(0f, 0f, 0f)
    private var accValues: FloatArray = floatArrayOf(0f, 0f, 0f)

    private var isMouseOn = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MouseScreen()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        gyroscopeListener = GyroscopeListener()
        gyroscopeManager = GyroscopeManager(sensorManager, gyroscopeListener)

        accelerometerListener = AccelerometerListener()
        accelerometerManager = AccelerometerManager(sensorManager, accelerometerListener)
    }

    fun toHome() {
        if (isMouseOn) {
            mouseDisable()
        }

        Thread {
            serverManager.sendTcp("disconnect")
        }.start()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun switchMouseOnOf() {
        isMouseOn = if (!isMouseOn) {
            mouseEnable()
            true
        } else {
            mouseDisable()
            false
        }
    }

    private fun mouseEnable() {
        moveTimer = Timer()
        moveTimer.schedule(0, 100) {
            val moveX: Int = (-gyroValues[2]*100).toInt()
            val moveY: Int = (-gyroValues[0]*100).toInt()

            Thread {
                serverManager.sendUdp("move,${moveX},${moveY}")
            }.start()
        }
    }

    private fun mouseDisable() {
        moveTimer.cancel()
    }

    fun mouseClick(type: String) {
        val command = "click,$type"

        Thread {
            serverManager.sendTcp(command)
        }.start()
    }

    fun onMouseScroll(type: String) {
        try {
            scrollTimer.cancel()
        } catch (_: Exception) {

        }

        scrollTimer = Timer()

        val command = "scroll,$type"

        scrollTimer.schedule(0, 100) {
            Thread {
                serverManager.sendTcp(command)
            }.start()
        }
    }

    fun offMouseScroll() {
        scrollTimer.cancel()
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