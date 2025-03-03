package com.example.remoconmouse.ui

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import com.example.remoconmouse.data.GyroscopeRepository
import com.example.remoconmouse.data.AccelerometerRepository
import com.example.remoconmouse.ServerData
import com.example.remoconmouse.utils.NetworkUtils

import java.util.Timer
import kotlin.concurrent.schedule

class MouseViewModel(application: Application) : AndroidViewModel(application) {
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var gyroscopeRepository = GyroscopeRepository(sensorManager)
    private var accelerometerRepository = AccelerometerRepository(sensorManager)

    private val serverManager = ServerData.serverManager
    private var moveTimer = Timer()
    private var scrollTimer = Timer()

    private var isMouseEnable = false

    var isConnected = false

    fun connect(ip: String) {
        isConnected = if (NetworkUtils.isIP(ip)) serverManager.connect(ip, 11000) else false
    }

    fun disconnect() {
        if (isMouseEnable) {
            mouseDisable()
        }

        Thread {
            serverManager.sendTcp("disconnect")
        }.start()

        isConnected = false
    }

    fun toggleMouseEnable() {
        isMouseEnable = if (!isMouseEnable) {
            mouseEnable()
            true
        } else {
            mouseDisable()
            false
        }
    }

    private fun mouseEnable() {
        registerSensors()
        moveTimer = Timer()
        moveTimer.schedule(0, 100) {
            val moveX: Int = (-gyroscopeRepository.values[2]*100).toInt()
            val moveY: Int = (-gyroscopeRepository.values[0]*100).toInt()

            Thread {
                serverManager.sendUdp("move,${moveX},${moveY}")
            }.start()
        }
    }

    private fun mouseDisable() {
        unregisterSensors()
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

    private fun registerSensors() {
        gyroscopeRepository.registerListener()
        accelerometerRepository.registerListener()
    }

    private fun unregisterSensors() {
        gyroscopeRepository.unregisterListener()
        accelerometerRepository.unregisterListener()
    }
}