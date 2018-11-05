package com.makhovyk.misteramkotlin.data

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("business-app/api/couriers/app")
    fun getAppToken(
        @Query("v") version: String,
        @Query("type") type: String,
        @Query("deviceId") deviceId: String
    ): Observable<Model.AppToken>

    @POST("business-app/api/couriers/account/sign-in")
    fun getAuthToken(
        @Query("username") username: String,
        @Query("password") password: String,
        @Header("App-Token") appToken: String
    ): Observable<Model.AuthToken>

    @GET("business-app/api/couriers/tasks/active")
    fun getActiveTasks(@Header("App-Auth-Token") authToken: String): Observable<List<Model.Task>>

    companion object {
        fun create(): ApiService {

            val interceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://test.mister.am/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}