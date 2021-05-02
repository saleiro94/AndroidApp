package com.example.giteste

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.giteste.api.EndPoints
import com.example.giteste.api.Pontos
import com.example.giteste.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class deleteUpdate : AppCompatActivity() {

    private lateinit var editponto: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_update)

        val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_login), Context.MODE_PRIVATE
        )
        editponto = findViewById(R.id.UpdatePontoEdit)

        var problema: String =intent.getStringExtra("Problema")

        val tudo = intent.getStringExtra("Tudo")
         val tudoaray = tudo!!.split("_").toTypedArray()
         val id = tudoaray[0].toInt()
        val id_tipo = tudoaray[5].toInt()
        val id_user = tudoaray[6].toInt()

        val tipoacidente = findViewById<View>(R.id.AcidenteE) as RadioButton
        val tipoobras = findViewById<View>(R.id.ObrasE) as RadioButton
        tipoacidente.setOnClickListener{
             var id_tipo=2
            Toast.makeText(this, id_tipo.toString(), Toast.LENGTH_SHORT).show()
        }
        tipoobras.setOnClickListener{
            var id_tipo=1
            Toast.makeText(this, id_tipo.toString(), Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(this@deleteUpdate, id_tipo.toString(), Toast.LENGTH_SHORT).show()

        findViewById<EditText>(R.id.UpdatePontoEdit).setText(problema)

        val buttondelete = findViewById<Button>(R.id.DeletePonto)

        buttondelete.setOnClickListener{
            Log.d("ads", id_user.toString() + (sharedPref.all[getString(R.string.loginDone)]))
            if(id_user.toString() == (sharedPref.all[getString(R.string.loginDone)])) {
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                Log.d("ads", id.toString())
                val call = request.delete(id)

                call.enqueue(object : Callback<Pontos> {
                    override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                        if (response.isSuccessful) {
                            val c: Pontos = response.body()!!

                            val intent = Intent(this@deleteUpdate, MapsActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<Pontos>, t: Throwable) {
                        Toast.makeText(this@deleteUpdate, getString(R.string.fail), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@deleteUpdate, MapsActivity::class.java)
                        startActivity(intent)
                    }

                })
            } else{
                Toast.makeText(this@deleteUpdate, getString(R.string.permissoes), Toast.LENGTH_SHORT).show()
            }
        }
        val buttonupdate = findViewById<Button>(R.id.UpdatePonto)

        buttonupdate.setOnClickListener{
            if(id_user.toString() == (sharedPref.all[getString(R.string.loginDone)])) {
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                Log.d("ads", "ad")
                val call = request.update(id, editponto.text.toString(), 2)

                call.enqueue(object : Callback<Pontos> {
                    override fun onResponse(call: Call<Pontos>, response: Response<Pontos>) {

                        if (response.isSuccessful) {
                            val c: Pontos = response.body()!!

                            val intent = Intent(this@deleteUpdate, MapsActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<Pontos>, t: Throwable) {
                        Toast.makeText(this@deleteUpdate, getString(R.string.fail), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@deleteUpdate, MapsActivity::class.java)
                        startActivity(intent)
                    }

                })
            }else{
                Toast.makeText(this@deleteUpdate, getString(R.string.permissoes), Toast.LENGTH_SHORT).show()
            }
        }
    }




}