package com.mohkhz.covid_19tracker_kotlin.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

open class AppConfig {
    companion object {
        private lateinit var retrofit: Retrofit
        private val url = "https://disease.sh/v3/covid-19/"

        open fun getRetrofit(): Retrofit {
            val retrofit by lazy {
                Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }

            this.retrofit = retrofit
            return this.retrofit
        }
    }

}