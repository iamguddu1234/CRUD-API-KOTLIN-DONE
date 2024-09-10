package com.example.learnk.Project

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://341c-2409-40c2-10a-e541-e527-ae83-e3bf-6f4b.ngrok-free.app"



    val api: NotesApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())  // RxJava Adapter
            .build()
            .create(NotesApi::class.java)
    }
}
