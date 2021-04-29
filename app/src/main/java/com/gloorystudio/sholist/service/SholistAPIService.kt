package com.gloorystudio.sholist.service

import com.gloorystudio.sholist.model.User
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SholistAPIService {

    //TODO: Base url eklenecek.
    private val BASE_URL=""

    private  val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(SholistAPI::class.java)

    /*
    fun getData(): Single<List<User>>{

    }
    */
}