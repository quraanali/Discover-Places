package com.quraanali.discoverPlaces.data.source.remote

import com.quraanali.discoverPlaces.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(appInterceptor: AppInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.addInterceptor(appInterceptor)
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logger)
        }
        //TODO : Review if the server needs cookies
        //builder.cookieJar(JavaNetCookieJar(CookieManager()))
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideApiEndPoints(retrofit: Retrofit): ApiEndPoints {
        return retrofit.create(ApiEndPoints::class.java)
    }

    @Singleton
    @Provides
    fun provideAppInterceptor(
    ): AppInterceptor {
        return AppInterceptor()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(moshi).withNullSerialization().asLenient()
            )
            .client(okHttpClient)
            .build()
    }

}
