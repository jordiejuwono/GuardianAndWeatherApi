package com.example.guardianapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.guardianapi.data.network.model.weatherapi.onecall.Hourly
import com.example.guardianapi.databinding.WeatherHourlyItemBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class WeatherHoursAdapter : RecyclerView.Adapter<WeatherHoursAdapter.WeatherViewHolder>() {

    private val items: MutableList<Hourly> = mutableListOf()

    class WeatherViewHolder(val binding: WeatherHourlyItemBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(WeatherHourlyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentPosition = items[position]
        val sdf = SimpleDateFormat("HH:mm")
        val dateTime = Date((currentPosition.dt?.toLong() ?: 0) * 1000)
        val weatherIcon = currentPosition.weather!![0].icon
        val temperature = currentPosition.temp?.minus(273.15)
        val temperatureFormat = DecimalFormat("##").format(temperature)

        holder.binding.tvTime.text = sdf.format(dateTime)
        holder.binding.ivWeather.load("https://openweathermap.org/img/wn/$weatherIcon.png") {
            crossfade(true)
        }
        holder.binding.tvWeather.text = currentPosition.weather!![0].main
        holder.binding.tvTemperature.text = temperatureFormat
    }

    override fun getItemCount(): Int = items.size

    private fun clearData() {
        this.items.clear()
    }

    fun setData(items: List<Hourly>){
        clearData()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}