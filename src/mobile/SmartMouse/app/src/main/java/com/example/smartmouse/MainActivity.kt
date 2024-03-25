package com.example.smartmouse

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartmouse.databinding.ActivityMainBinding
import com.example.smartmouse.SensorInterface

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var mSensorManager: SensorManager
    private var mGyroscope: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        val sensor = SensorInterface().testFun()

        binding.valueX.text = sensor.x.toString()
        binding.valueY.text = sensor.y.toString()
        binding.valueZ.text = sensor.z.toString()
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this as SensorEventListener, mGyroscope, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this as SensorEventListener)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            binding.valueX.text = event.values[0].toString()
            binding.valueY.text = event.values[1].toString()
            binding.valueZ.text = event.values[2].toString()
        }
    }
}