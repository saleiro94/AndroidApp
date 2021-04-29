package com.example.giteste

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.giteste.api.EndPoints
import com.example.giteste.api.OutputPost
import com.example.giteste.api.Pontos
import com.example.giteste.api.ServiceBuilder
import com.google.android.gms.location.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovoPonto : AppCompatActivity(){


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback : LocationCallback
    private lateinit var locationRequest : LocationRequest
    private lateinit var lastLocation : Location
    private val LOCATION_PERMISSION_REQUEST_CODE = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_ponto)

        val sharedPref: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_login), Context.MODE_PRIVATE
        )

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                Log.d("pontos", "coordenadas"+ loc.latitude+ "- "+ loc.longitude)
            }
        }
        createLocationRequest()



        val pontoproblema = findViewById<EditText>(R.id.pontoproblema)

        val id_Users = sharedPref.all[getString(R.string.loginDone)].toString()

        val button = findViewById<Button>(R.id.login)
        button.setOnClickListener{
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.postNovoPonto(id = 0,pontoproblema.toString(),lastLocation.latitude,lastLocation.longitude,id_Users.toInt())

            call.enqueue(object : Callback<Pontos> {
                override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                    if (response.isSuccessful) {
                        val c: Pontos = response.body()!!

                        //Shared Preferences Login

                        if (R.string.loginDone == 2) {
                            Toast.makeText(this@NovoPonto, "consegui ler", Toast.LENGTH_SHORT).show()
                        }
                        //Toast.makeText(this@Login,R.string.LoginShared, Toast.LENGTH_SHORT).show()
                        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                        finish()
                    }
                }

                override fun onFailure(call: Call<Pontos>, t: Throwable) {
                    Toast.makeText(this@NovoPonto, "FAil", Toast.LENGTH_SHORT).show()
                }

            })


        }
    }

    private fun startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,null)
    }




    private  fun createLocationRequest(){
        locationRequest = LocationRequest()

        locationRequest.interval=1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }



    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }




}