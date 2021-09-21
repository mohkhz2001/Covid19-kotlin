package com.mohkhz.covid_19tracker_kotlin.API

import com.mohkhz.covid_19tracker_kotlin.Model.CountriesInfoList
import com.mohkhz.covid_19tracker_kotlin.Model.CountryName
import com.mohkhz.covid_19tracker_kotlin.Model.Total
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.ArrayList

open interface ApiRequest {

    @GET("all")
    fun getAll(): Call<Total>

    @GET("countries")
    fun getCountries(): Call<CountriesInfoList>

    @GET("{country}?lastdays={days}")
    fun getHistory(
        @Path("country") name: String,
        @Path("days") days: Int
    ): Call<CountriesInfoList>

    @GET("historical?lastdays=1")
    fun getHistoryCountry(): Call<ArrayList<CountryName>>

}