package com.quraanali.discoverPlaces.data.source.remote

import com.quraanali.discoverPlaces.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AppInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newBuilder = original.newBuilder()


        newBuilder.addHeader("Accept", "application/json")
        newBuilder.addHeader("Authorization", "Client-ID " + BuildConfig.UNSPLASH_API_AUTH)


        val request = newBuilder.build()
        return chain.proceed(request)
    }
}