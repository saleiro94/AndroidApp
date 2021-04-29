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


    private lateinit var pontoproblema: EditText
    private lateinit var tipoproblema: EditText
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



       pontoproblema = findViewById<EditText>(R.id.pontoproblema)
        tipoproblema = findViewById<EditText>(R.id.tipoproblema)

        val id_Users = sharedPref.all[getString(R.string.loginDone)].toString()


        val button = findViewById<Button>(R.id.Novoponto)
        button.setOnClickListener{
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            Log.d("dados", pontoproblema.text.toString() +lastLocation.latitude.toString()+lastLocation.longitude.toString()+"----"+ id_Users+"----"+tipoproblema.text.toString().toInt())
            val call = request.postNovoPonto(pontoproblema.text.toString(),lastLocation.latitude,lastLocation.longitude,id_Users.toInt(),tipoproblema.text.toString().toInt())

            call.enqueue(object : Callback<Pontos> {
                override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                    if (response.isSuccessful) {
                        val c: Pontos = response.body()!!

                        val intent = Intent(this@NovoPonto, MapsActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<Pontos>, t: Throwable) {
                    Toast.makeText(this@NovoPonto, "FAil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@NovoPonto, MapsActivity::class.java)
                    startActivity(intent)
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