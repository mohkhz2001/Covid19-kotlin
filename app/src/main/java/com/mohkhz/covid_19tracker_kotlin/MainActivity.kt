package com.mohkhz.covid_19tracker_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigation.NavigationBarView
import com.mohkhz.covid_19tracker_kotlin.API.ApiRequest
import com.mohkhz.covid_19tracker_kotlin.API.AppConfig
import com.mohkhz.covid_19tracker_kotlin.Fragments.CountryFragment
import com.mohkhz.covid_19tracker_kotlin.Fragments.HomeFragment
import com.mohkhz.covid_19tracker_kotlin.Model.Total
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var request: ApiRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        request = AppConfig.getRetrofit().create(ApiRequest::class.java)

        start()
        controllerViews()
    }

    private fun controllerViews() {
        navigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    val homeFragment = HomeFragment()
                    var a = supportFragmentManager.beginTransaction()
                    a.replace(R.id.frameLayout, homeFragment).commit()
                    return@OnItemSelectedListener true
                }
                R.id.nav_country -> {
                    val countryFragment = CountryFragment()
                    val a = supportFragmentManager.beginTransaction()
                    a.replace(R.id.frameLayout, countryFragment).commit()
                    return@OnItemSelectedListener true
                }
                else -> return@OnItemSelectedListener false
            }
        })
    }

    private fun makeApiRequest() {
        val response = request.getAll()

        response.enqueue(object : Callback<Total> {
            override fun onResponse(call: Call<Total>, response: Response<Total>) {
                val a = response.body()
            }

            override fun onFailure(call: Call<Total>, t: Throwable) {
                println()
//                weatherData!!.text = t.message
            }
        })
    }

    private fun start() {
        var a = supportFragmentManager.beginTransaction()
        a.replace(R.id.frameLayout, HomeFragment()).commit()
    }

}
