package com.example.calmsleep

import com.example.calmsleep.pojo.pojo
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {


    @GET("chinmayee232000/repos")
    fun getData(): Call<ArrayList<pojo>>
}