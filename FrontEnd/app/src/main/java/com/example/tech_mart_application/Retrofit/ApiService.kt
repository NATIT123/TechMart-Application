package com.example.tech_mart_application.Retrofit


import com.example.tech_mart_application.models.DataResponse
import com.example.tech_mart_application.models.User
import com.example.tech_mart_application.utils.Constants.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    companion object {
        private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

        val apiService: ApiService by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create(
                    gson
                )
            ).client(client).build().create(ApiService::class.java)
        }
    }


    @POST("user/login")
    fun loginUser(@Body user: User): Call<DataResponse>

    @POST("user/register")
    fun registerUser(@Body user: User): Call<DataResponse>


}