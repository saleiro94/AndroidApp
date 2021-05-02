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
    @GET("/myslim/ponto")
    fun getPontos():Call<List<Pontos>>
    @FormUrlEncoded
    @POST("/myslim/novoponto")
    fun postNovoPonto(@Field("problema") problema: String,@Field("lat") lat: Double,@Field("lng") lng: Double,@Field("id_Users") id_Users: Int,@Field("id_Tipo") id_Tipo: Int): Call<Pontos>
    @FormUrlEncoded
    @POST("/myslim/delete")
    fun delete(@Field("id") id: Int): Call<Pontos>
    @FormUrlEncoded
    @POST("/myslim/update")
    fun update(@Field("id") id: Int,@Field("problema") problema: String,@Field("id_Tipo") id_Tipo: Int): Call<Pontos>

}