package com.example.giteste


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.giteste.api.EndPoints
import com.example.giteste.api.OutputPost
import com.example.giteste.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var prox: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        prox = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
       //verifica se o login já foi realizado - SharedPreferences
        val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_login), Context.MODE_PRIVATE
        )
        if (sharedPref != null){
            if(sharedPref.all[getString(R.string.LoginShared)]== true){
                var intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
        }

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        var intent = Intent(this, MapsActivity::class.java)

            val button = findViewById<Button>(R.id.login)
            button.setOnClickListener{
                if (TextUtils.isEmpty(username.text) || TextUtils.isEmpty(password.text)) {
                    Toast.makeText(this@Login, "Algum dos Campos encontram-se vazios", Toast.LENGTH_SHORT).show()
                }else {
                    val request = ServiceBuilder.buildService(EndPoints::class.java)
                    val call = request.postLogin(username.text.toString(), password.text.toString())

                    call.enqueue(object : Callback<OutputPost> {
                        override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                            if (response.isSuccessful) {
                                val c: OutputPost = response.body()!!

                                //Shared Preferences Login
                                val sharedPref: SharedPreferences = getSharedPreferences(
                                        getString(R.string.preference_login), Context.MODE_PRIVATE
                                )
                                with(sharedPref.edit()) {
                                    putBoolean(getString(R.string.LoginShared), true)
                                    putString(getString(R.string.loginDone), c.id.toString())
                                    commit()
                                    Log.d("**SHARED", "${c.id}")
                                }


                                //Toast.makeText(this@Login,R.string.LoginShared, Toast.LENGTH_SHORT).show()
                                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)

                                finish()
                            }
                        }

                        override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                            Toast.makeText(this@Login, getString(R.string.fail), Toast.LENGTH_SHORT).show()
                        }

                    })

                }
            }

    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
        return
    }

    override fun onSensorChanged(event: SensorEvent) {
        val value = event.values[0]
        Log.d("prox",value.toString())
        val teste = findViewById<Button>(R.id.login)
        // Do something with this sensor data.
        if(value <15){
            teste.setBackgroundColor(Color.BLUE)
        }else{
            teste.setBackgroundColor(Color.RED)
        }


    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        sensorManager.registerListener(this, prox, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    }


