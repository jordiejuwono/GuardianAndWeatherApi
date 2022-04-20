package com.example.guardianapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.guardianapi.data.network.model.weatherapi.onecall.Daily
import com.example.guardianapi.databinding.WeatherDayItemBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class WeatherDailyAdapter : RecyclerView.Adapter<WeatherDailyAdapter.WeatherViewHolder>() {

    private val items: MutableList<Daily> = mutableListOf()

    class WeatherViewHolder(val binding: WeatherDayItemBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(WeatherDayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentPosition = items[position]
        val sdf = SimpleDateFormat("MMM dd")
        val sdfDay = SimpleDateFormat("EEEE")
        val dateTime = Date((currentPosition.dt?.toLong() ?: 0) * 1000)
        val weatherIcon = currentPosition.weather!![0].icon
        val temperatureMax = currentPosition.temp?.max?.minus(273.15)
        val temperatureMin = currentPosition.temp?.min?.minus(273.15)
        val temperatureMaxFormat = DecimalFormat("##").format(temperatureMax)
        val temperatureMinFormat = DecimalFormat("##").format(temperatureMin)

//        holder.binding.tvDate.text = sdf.format(dateTime)

        if (position == 0){
            holder.binding.tvDay.text = "Today"
        } else {
            holder.binding.tvDay.text = sdfDay.format(dateTime)
        }

        holder.binding.ivIcon.load("https://openweathermap.org/img/wn/$weatherIcon.png") {
            crossfade(true)
        }
//        holder.binding.tvWeather.text = currentPosition.weather!![0].description
        holder.binding.tvWeather.text = currentPosition.weather!![0].main
        holder.binding.tvTemperatureMax.text = temperatureMaxFormat
        holder.binding.tvTemperatureMin.text = temperatureMinFormat
    }

    override fun getItemCount(): Int = (items.size)-1

    private fun clearItems() {
        this.items.clear()
    }

    fun setData(items: List<Daily>){
        clearItems()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}