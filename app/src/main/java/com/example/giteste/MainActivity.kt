package com.example.giteste

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var pressure: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)


    }

    fun bnotas(view: View){
        val button = findViewById<Button>(R.id.notas)
        button.setOnClickListener{
            val intent = Intent(this, Notas::class.java)
            startActivity(intent)
        }
    }
    fun blogin(view: View){
        val button = findViewById<Button>(R.id.blogin)

        button.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }


    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
        return
    }

    override fun onSensorChanged(event: SensorEvent) {
        val value = event.values[0]
        val teste = findViewById<Button>(R.id.blogin)
        // Do something with this sensor data.
       if(value <50){
           teste.setBackgroundColor(Color.BLUE)
       }else{
           teste.setBackgroundColor(Color.RED)
       }


    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}