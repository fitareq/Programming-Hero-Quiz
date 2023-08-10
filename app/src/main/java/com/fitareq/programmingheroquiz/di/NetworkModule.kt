package com.fitareq.programmingheroquiz.di

import com.fitareq.programmingheroquiz.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val baseUrl = "https://herosapp.nyc3.digitaloceanspaces.com/"
    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun providesApiService(
        retrofit: Retrofit
    ):ApiService{
        return retrofit.create(ApiService::class.java)
    }
}