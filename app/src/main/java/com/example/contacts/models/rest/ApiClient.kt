package com.example.contacts.models.rest

import com.example.contacts.models.User
import com.example.contacts.models.UserEdit
import com.example.contacts.models.rest.entity.LoginResponse
import com.example.contacts.models.rest.entity.ResultResponse
import com.example.contacts.models.rest.entity.UsersResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiClient {

    @POST("login.php")
    fun login(@Query("email") email: String, @Query("password") password: String): Deferred<LoginResponse>

    @GET("login.php")
    fun login2(@Query("Authorization") authorization: String): Deferred<LoginResponse>

    @GET("users.php")
    fun getUsers(@Query("token") token: String?): Deferred<UsersResponse>

    @GET("user.php")
    fun getUser(@Query("token") token: String): Deferred<User>

    @POST("user.php")
    fun registerUser(@Body user: User): Deferred<LoginResponse>

    @PUT("user.php")
    fun editUser(@Query("token") token: String, @Body user: UserEdit): Deferred<ResultResponse>

    @GET("logout.php")
    fun logout(@Query("token") token: String): Deferred<ResultResponse>

    companion object {
        operator fun invoke(): ApiClient {


            val headerAuthorizationInterceptor = Interceptor{ chain ->

                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization","Basic dG9taToxMjM0")
                    .build()

                return@Interceptor chain.proceed(request)
            }


            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(headerAuthorizationInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://creativecore.com.ar/capacitacion/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiClient::class.java)
        }
    }

}