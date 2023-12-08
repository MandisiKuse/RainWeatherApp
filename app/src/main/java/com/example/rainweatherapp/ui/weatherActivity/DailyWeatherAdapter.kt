package com.example.rainweatherapp.ui.weatherActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.rainweatherapp.R
import com.example.rainweatherapp.models.WeatherData
import kotlinx.android.synthetic.main.activity_weather.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class DailyWeatherAdapter( private var weatherdatalist: List<WeatherData>)
    :RecyclerView.Adapter<ItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylerview_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return weatherdatalist.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val weatherData = weatherdatalist[position]
        val date=  LocalDate.parse(weatherData.dt_txt.subSequence(0,10), DateTimeFormatter.ISO_DATE)
        holder.day.text =date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        holder.temp.text = weatherData.main.temp.toInt().toString()
        if(weatherData.weather[0].main.equals("Rain"))
            holder.icon.setImageDrawable(getDrawable(holder.itemView.context,R.drawable.rain3))

        if(weatherData.weather[0].main.equals("Clouds"))
            holder.icon.setImageDrawable(getDrawable(holder.itemView.context,R.drawable.partlysunny3))


    }
}

class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var day: TextView = itemView.findViewById(R.id.day)
    var icon: ImageView = itemView.findViewById(R.id.icon)
    var temp: TextView = itemView.findViewById(R.id.temp)


}

