package com.mohkhz.covid_19tracker_kotlin.API

import com.mohkhz.covid_19tracker_kotlin.Model.CountriesInfoList
import com.mohkhz.covid_19tracker_kotlin.Model.Total
import retrofit2.Call
import retrofit2.http.GET

open interface ApiRequest {

    @GET("all")
    fun getAll(): Call<Total>

    @GET("countries")
    fun getCountries(): Call<CountriesInfoList>

}