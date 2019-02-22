package com.example.contacts.models.rest.base

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceInterceptor @Inject constructor():Interceptor {

    private lateinit var auth: String

    fun setToken(authHeader: String){
        auth = authHeader
    }

    fun getToken(): String {
        return auth
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", auth)
            .build()

        return chain.proceed(request)
    }
}