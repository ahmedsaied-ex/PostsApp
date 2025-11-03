package com.example.newmovieapp.di

import android.content.Context
import com.example.moviesap.BuildConfig
import com.example.newmovieapp.data.remote.PostsApi
import com.example.newmovieapp.data.remote.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // ✅ Provide the custom interceptor
    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(
        @ApplicationContext context: Context
    ): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context)
    }

    // ✅ Provide OkHttpClient with the interceptor
    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // ✅ Provide Retrofit instance
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // ✅ Provide API implementation
    @Provides
    @Singleton
    fun providePostsApi(retrofit: Retrofit): PostsApi {
        return retrofit.create(PostsApi::class.java)
    }
}
