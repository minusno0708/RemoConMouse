package com.example.remoconmouse.data;

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.remoconmouse.viewmodel.MouseViewModel

class GyroscopeRepository(private val sensorManager: SensorManager) : SensorEventListener {
    private var gyroscopeSensor: Sensor? = null
    var values: FloatArray = floatArrayOf(0f, 0f, 0f)

    init {
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
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
            values = floatArrayOf(event.values[0], event.values[1], event.values[2])
        }
    }
}
