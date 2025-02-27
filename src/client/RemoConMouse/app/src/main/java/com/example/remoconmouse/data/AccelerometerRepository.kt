package com.example.remoconmouse.data

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class AccelerometerRepository (private val sensorManager: SensorManager) : SensorEventListener {
    private var accelerometerSensor: Sensor? = null
    private var gravitySensor: Sensor? = null

    private var gravityValues: FloatArray = floatArrayOf(0f, 0f, 0f)
    private var values: FloatArray = floatArrayOf(0f, 0f, 0f)

    init {
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    }

    fun registerListener() {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            values = floatArrayOf(event.values[0]-gravityValues[0], event.values[1]-gravityValues[1], event.values[2]-gravityValues[2])
        } else if (event.sensor.type == Sensor.TYPE_GRAVITY) {
            gravityValues = event.values.clone()
        }
    }
}