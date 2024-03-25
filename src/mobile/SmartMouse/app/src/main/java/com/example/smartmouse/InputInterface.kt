package com.example.smartmouse

import android.hardware.Sensor
import android.hardware.SensorEvent

class Coordinate(var x: Float, var y: Float, var z: Float) {}

class SensorInterface {
    fun onGyroscopeChanged(event: SensorEvent): Coordinate {
        val coo = Coordinate(0f, 0f, 0f)

        if (event.sensor.type === Sensor.TYPE_GYROSCOPE) {
            coo.x = event.values[0]
            coo.y = event.values[1]
            coo.z = event.values[2]
        }

        return coo
    }

    fun testFun(): Coordinate {
        return Coordinate(0f, 0f, 0f)
    }
}