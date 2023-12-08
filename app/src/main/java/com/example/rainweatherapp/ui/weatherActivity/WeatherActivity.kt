package com.example.rainweatherapp.ui.weatherActivity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rainweatherapp.R
import com.example.rainweatherapp.models.Root
import com.example.rainweatherapp.models.WeatherData
import kotlinx.android.synthetic.main.activity_weather.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherActivity : AppCompatActivity() {
    lateinit var weatherData: Root
    private lateinit var itemsAdapter: DailyWeatherAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        weatherData = intent.getSerializableExtra("WeatherData") as Root
        initView()

        itemsAdapter =
            DailyWeatherAdapter(
                getFivedayList(weatherData.list)
            )
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = layoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = itemsAdapter


    }

    fun initView() {
        val description = weatherData.list.first().weather.first().main

        if (description.equals("Rain")) {
            img_background.setImageDrawable(getDrawable(R.drawable.forest_rainy))
            weatheractivity.setBackgroundColor(getColor(R.color.rain))
        }
        if (description.equals("Clouds")) {
            img_background.setImageDrawable(getDrawable(R.drawable.forest_cloudy))
            weatheractivity.setBackgroundColor(getColor(R.color.cloudy))
        }

        tv_maintemp.text =
            weatherData.list.first().main.temp.toInt().toString() //removes decimal places
        tv_desc.text = description
        tv_min.text = weatherData.list.first().main.temp_min.toInt().toString()
        tv_current.text = tv_maintemp.text
        tv_max.text = weatherData.list.first().main.temp_max.toInt().toString()


    }
        //manual extraction : api returns hourly updates

    private fun getFivedayList(list: List<WeatherData>): List<WeatherData> {
        var tempDate = LocalDate.now().dayOfWeek
        var trimmedList = listOf<WeatherData>().toMutableList()
        for (weatherItem in list) {

            val date = LocalDate.parse(weatherItem.dt_txt.subSequence(0, 10), DateTimeFormatter.ISO_DATE)

            if (  trimmedList.size != 5 && tempDate != date.dayOfWeek) {

                  tempDate=date.dayOfWeek
                  trimmedList.add(weatherItem)

            }
        }
            trimmedList.add(0,list.first())

        return trimmedList
    }
}
