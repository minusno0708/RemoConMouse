package com.example.smartmouse

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class GyroscopeManager(private val sensorManager: SensorManager, private val listener: GyroscopeListener): SensorEventListener {
    private var gyroscopeSensor: Sensor? = null

    init {
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    interface GyroscopeListener {
        fun onValueChanged(x: Float, y: Float, z: Float)
    }

    fun registerListener() {
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            listener.onValueChanged(event.values[0], event.values[1], event.values[2])
        }
    }
}

class AccelerometerManager(private val sensorManager: SensorManager, private val listener: AccelerometerListener): SensorEventListener {
    private var accelerometerSensor: Sensor? = null

    init {
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    interface AccelerometerListener {
        fun onValueChanged(x: Float, y: Float, z: Float)
    }

    fun registerListener() {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            listener.onValueChanged(event.values[0], event.values[1], event.values[2])
        }
    }
}