package com.mohkhz.covid_19tracker_kotlin.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mohkhz.covid_19tracker_kotlin.API.ApiRequest
import com.mohkhz.covid_19tracker_kotlin.API.AppConfig
import com.mohkhz.covid_19tracker_kotlin.Model.Total
import com.mohkhz.covid_19tracker_kotlin.R
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class HomeFragment : Fragment() {

    lateinit var request: ApiRequest
    lateinit var total: Total

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        request = AppConfig.getRetrofit().create(ApiRequest::class.java)

        makeApiRequest()


        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setValue(total: Total) {
        val decimalFormat = DecimalFormat("###,###") // set the decimal format

        NewConfirmed.text = decimalFormat.format(total.todayCases)
        NewRecovered.text = decimalFormat.format(total.todayRecovered)
        NewDeaths.text = decimalFormat.format(total.todayDeaths)
        TotalConfirmed.text = decimalFormat.format(total.cases)
        TotalDeaths.text = decimalFormat.format(total.deaths)
        TotalRecovered.text = decimalFormat.format(total.recovered)

        spin_kit.visibility = View.GONE
        connect.visibility = View.GONE
        constraintLayout.visibility = View.VISIBLE

    }

    private fun makeApiRequest() {

        val response = request.getAll()

        response.enqueue(object : Callback<Total> {
            override fun onResponse(call: Call<Total>, response: Response<Total>) {
                if (response.body()?.active != null) {
                    total = response.body()!!
                    setValue(total)
                } else {
                    Toast.makeText(context, "", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Total>, t: Throwable) {
                println()
                Toast.makeText(context, "", Toast.LENGTH_LONG).show()
            }
        })

    }


}