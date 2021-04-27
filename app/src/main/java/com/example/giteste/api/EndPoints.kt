package com.example.giteste.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface EndPoints {
    @FormUrlEncoded
    @POST("/myslim/login")
    fun postLogin(@Field("nome") nome :String,@Field("password") password: String): Call<OutputPost>

}