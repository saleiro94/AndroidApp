package com.example.giteste.api

import retrofit2.http.POST

interface EndPoints {

    @POST("/login")
    fun postLogin()
}