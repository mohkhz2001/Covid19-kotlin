package com.mohkhz.covid_19tracker_kotlin.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.mohkhz.covid_19tracker_kotlin.API.ApiRequest
import com.mohkhz.covid_19tracker_kotlin.API.AppConfig
import com.mohkhz.covid_19tracker_kotlin.Model.CountriesInfoList
import com.mohkhz.covid_19tracker_kotlin.Model.CountryInfo
import com.mohkhz.covid_19tracker_kotlin.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_country.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class CountryFragment : Fragment() {

    var nameList = MutableList<String>(0, { "" })
    lateinit var request: ApiRequest
    var countriesInfo = ArrayList<CountryInfo>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        request = AppConfig.getRetrofit().create(ApiRequest::class.java)

        makeApiRequest()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    private fun makeApiRequest() {
        val response = request.getCountries()

        response.enqueue(object : Callback<CountriesInfoList> {
            override fun onResponse(
                call: Call<CountriesInfoList>,
                response: Response<CountriesInfoList>
            ) {
                countriesInfo = response.body()!!
                setSpinner()
            }

            override fun onFailure(call: Call<CountriesInfoList>, t: Throwable) {
                print("")
            }
        })
    }

    fun setSpinner() {

        for (name in countriesInfo)
            nameList.add(name.country)

        var arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nameList)

        spinner.adapter = arrayAdapter

        setValue()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                setValue(i)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        spin_kit.visibility = GONE
        connect.visibility = GONE
        constraintLayout.visibility = VISIBLE
    }

    fun setValue(choose: Int = 0) {

        val decimalFormat = DecimalFormat("###,###") // set the decimal format

        val countryChoosed = countriesInfo[choose]
        if (countryChoosed.todayDeaths == 0 && countryChoosed.todayCases == 0 && countryChoosed.todayRecovered == 0) {
            txt_patient_number.text = "N/A"
            txt_Recovered_number.text = "N/A"
            txt_death_number.text = "N/A"
        } else {
            txt_patient_number.text = decimalFormat.format(countryChoosed.todayCases)
            txt_Recovered_number.text = decimalFormat.format(countryChoosed.todayRecovered)
            txt_death_number.text = decimalFormat.format(countryChoosed.todayDeaths)
        }

        txt_patient_all.text = decimalFormat.format(countryChoosed.cases)
        txt_recovered_all.text = decimalFormat.format(countryChoosed.recovered)
        txt_death_all.text = decimalFormat.format(countryChoosed.deaths)
        Picasso.get().load(choose).into(flag)

    }

}

