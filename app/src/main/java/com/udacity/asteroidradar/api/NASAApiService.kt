package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.models.PictureOfDay

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.nasa.gov/"
// TODO Use the Moshi Builder to create a Moshi object with the KotlinJsonAdapterFactory
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    //TODO Change the ConverterFactory to the MOshiConverterFactory with our Moshi object
    //.addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AsteroidsApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getFeeds(@Query("start_date") startDate: String, @Query("api_key") apiKey: String): String
    @GET("planetary/apod")
    suspend fun getImageOfDay(@Query("api_key") apiKey: String): PictureOfDay
}

object  AsteroidsApi {
    val retrofitService : AsteroidsApiService by lazy {
        retrofit.create(AsteroidsApiService::class.java)
    }
}