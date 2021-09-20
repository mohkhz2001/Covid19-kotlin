package com.mohkhz.covid_19tracker_kotlin.Model

data class CountryInfo(
    val _id: Int,
    val flag: String,
    val iso2: String,
    val iso3: String,
    val lat: Float,
    val long: Float
)