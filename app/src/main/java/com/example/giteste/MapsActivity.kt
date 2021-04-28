package com.example.giteste

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.giteste.api.EndPoints
import com.example.giteste.api.Pontos
import com.example.giteste.api.ServiceBuilder

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var pontos: List<Pontos>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.frg) as SupportMapFragment
        mapFragment.getMapAsync(this)





        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {

            val sharedPref: SharedPreferences = getSharedPreferences(
                    getString(R.string.preference_login), Context.MODE_PRIVATE
            )
            with(sharedPref.edit()){
                putBoolean(getString(R.string.LoginShared), false)

                commit()

            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontos()
        val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_login), Context.MODE_PRIVATE
        )
        var posicao :LatLng
//asd
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
       // Toast.makeText(this@MapsActivity, R.string.loginDone, Toast.LENGTH_SHORT).show()
        Log.d("ponto","entrei no call")
        call.enqueue(object : Callback<List<Pontos>> {
            override fun onResponse(call: Call<List<Pontos>>, response: Response<List<Pontos>>) {
                if (response.isSuccessful){
                    pontos = response.body()!!
                    Log.d("ponto",pontos.toString())
                    for(ponto in pontos){
                        posicao = LatLng(ponto.lat, ponto.lng)
                        Log.d("ponto","${posicao}" +"${ponto.id_Users}"+sharedPref.all[getString(R.string.loginDone)])
                        //se o id_Utilizador do ponto for igual ao id do login(SharedPreferences
                        if (ponto.id_Users.toString() == (sharedPref.all[getString(R.string.loginDone)])){

                            mMap.addMarker(MarkerOptions()
                                    .position(posicao)
                                    .title(ponto.problema)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)) //altera a cor

                            )
                        }else {
                            mMap.addMarker(
                                    MarkerOptions()
                                            .position(posicao)
                                            .title(ponto.problema)
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Pontos>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

