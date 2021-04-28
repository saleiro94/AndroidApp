package com.example.giteste


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

                            if (R.string.loginDone == 2) {
                                Toast.makeText(this@Login, "consegui ler", Toast.LENGTH_SHORT).show()
                            }
                            //Toast.makeText(this@Login,R.string.LoginShared, Toast.LENGTH_SHORT).show()
                            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)

                            finish()
                        }
                    }

                    override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                        Toast.makeText(this@Login, "FAil", Toast.LENGTH_SHORT).show()
                    }

                })


            }

    }

    }


