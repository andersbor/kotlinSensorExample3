package com.example.sensorexample

import androidx.appcompat.app.AppCompatActivity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.sensorexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var proximitySensor: Sensor? = null
    private var accelerationSensor: Sensor? = null
    private var allSensors: List<Sensor>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        Log.d("ALL SENSORS", allSensors.toString())
    }

    override fun onResume() {
        super.onResume()
        Log.d("APPLE", "onResume")
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, accelerationSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        Log.d("APPLE", "onPause")
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val sensorName = event.sensor?.name
            when (event.sensor.type) {
                Sensor.TYPE_LIGHT -> {
                    val light: Float = event.values[0]
                    Log.d("APPLE", "Light $light")
                    binding.sensorView1.text = "$sensorName Light $light"
                }

                Sensor.TYPE_PROXIMITY -> {
                    val distanceInCm = event.values[0]
                    Log.d("APPLE", "Proximity $distanceInCm cm")
                    binding.sensorView2.text = "$sensorName Proximity $distanceInCm"
                }

                Sensor.TYPE_ACCELEROMETER -> {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]
                    binding.sensorView3.text = "$sensorName Acceleration $x $y $z"
                    Log.d("APPLE", "Acceleration: $x $y $z")
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("APPLE", "onAccuracyChanged")
    }
}