package com.example.guardianapi.ui.weather

import android.graphics.drawable.AnimationDrawable
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import coil.load
import com.example.guardianapi.R
import com.example.guardianapi.base.arch.BaseFragment
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.weatherapi.onecall.Daily
import com.example.guardianapi.data.network.model.weatherapi.onecall.Hourly
import com.example.guardianapi.data.network.model.weatherapi.onecall.WeatherOneCallResponse
import com.example.guardianapi.databinding.FragmentWeatherBinding
import com.example.guardianapi.ui.adapter.WeatherDailyAdapter
import com.example.guardianapi.ui.adapter.WeatherHoursAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class WeatherFragment :
    BaseFragment<FragmentWeatherBinding, WeatherViewModel>(FragmentWeatherBinding::inflate),
    WeatherContract.View {

    private lateinit var adapter: WeatherHoursAdapter
    private lateinit var adapterDaily: WeatherDailyAdapter

    override fun initView() {
        getData()
        setOnRefreshListener()
        initListWeather()
    }

    override fun getData() {
        getViewModel().getWeatherOneCallData()
    }

    override fun initListWeather() {
        adapter = WeatherHoursAdapter()
        adapterDaily = WeatherDailyAdapter()

        getViewBinding().rvTemperatureHourly.apply {
            adapter = this@WeatherFragment.adapter
        }
        getViewBinding().rvTemperatureDay.adapter = adapterDaily

        ViewCompat.setNestedScrollingEnabled(getViewBinding().rvTemperatureHourly, false)
        ViewCompat.setNestedScrollingEnabled(getViewBinding().rvTemperatureDay, false)
    }

    override fun setDataAdapter(data: List<Hourly>) {
        data.let { result ->
            adapter.setData(result)
        }
    }

    override fun setDataAdapterForDaily(data: List<Daily>) {
        data.let { result ->
            adapterDaily.setData(result)
        }
    }

    override fun setOnRefreshListener() {
        getViewBinding().srlContentWeather.setOnRefreshListener {
            getViewBinding().srlContentWeather.visibility = View.GONE
            getViewBinding().srlContentWeather.isRefreshing = false
            getData()
            observeData()
            initListWeather()
            showLoading(true)
        }
    }

    override fun setDataToView(response: WeatherOneCallResponse) {
        val temperature = response.current?.temp?.minus(273.15)
        val temperatureFelt = response.current?.feelsLike?.minus(273.15)
        val temperatureMin = response.daily!![0].temp?.min?.minus(273.15)
        val temperatureMax = response.daily!![0].temp?.max?.minus(273.15)
        val windSpeed = response.current?.windSpeed
        val temperatureResponse = DecimalFormat("##").format(temperature)
        val temperatureFeelsLike = DecimalFormat("##").format(temperatureFelt)
        val temperatureMinFormat = DecimalFormat("##").format(temperatureMin)
        val temperatureMaxFormat = DecimalFormat("##").format(temperatureMax)
        val windSpeedFormat = DecimalFormat("##").format(windSpeed)

        getViewBinding().tvTimezone.text = getString(R.string.text_jakarta)
        getViewBinding().tvTemperature.text = temperatureResponse?.toString()
        getViewBinding().tvTemperatureFelt.text = temperatureFeelsLike?.toString()
        getViewBinding().tvTemperatureMin.text = temperatureMinFormat.toString()
        getViewBinding().tvTemperatureMax.text = temperatureMaxFormat.toString()
        getViewBinding().tvWeatherMain.text = response.current?.weather!![0].main
        getViewBinding().tvAirPressure.text = response.current?.pressure.toString()
        getViewBinding().tvHumidity.text = response.current?.humidity.toString()
        getViewBinding().tvWindSpeed.text = windSpeedFormat.toString()

        when (getViewBinding().tvWeatherMain.text.toString().trim().lowercase()) {
            getString(R.string.text_clouds) -> {
                getViewBinding().ivWeather.load(R.drawable.ic_clouds) {
                    crossfade(true)
                }
            }
            getString(R.string.text_thunderstorm) -> {
                getViewBinding().ivWeather.load(R.drawable.ic_thunderstorm) {
                    crossfade(true)
                }
            }
            else -> {
                getViewBinding().ivWeather.load(R.drawable.ic_rain) {
                    crossfade(true)
                }
            }
        }

    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().srlContentWeather.isVisible = isVisible
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbLoadingWeather.isVisible = isVisible
    }

    override fun observeData() {
        getViewBinding().srlContentWeather.visibility = View.GONE

        getViewModel().getWeatherOneCallLiveData().observe(this) { responseOneCall ->
            when (responseOneCall) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    showError(false, null)
                    getViewBinding().srlContentWeather.visibility = View.VISIBLE
                    //recent report
                    responseOneCall.data?.let {
                        setDataToView(it)
                    }
                    //hourly report
                    responseOneCall.data?.hourly?.let {
                        setDataAdapter(it)
                    }
                    //daily report
                    responseOneCall.data?.daily?.let {
                        setDataAdapterForDaily(it)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, responseOneCall.message)
                }
            }
        }
    }

}