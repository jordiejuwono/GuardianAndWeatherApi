package com.example.guardianapi.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guardianapi.base.arch.BaseViewModelImpl
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.weatherapi.onecall.WeatherOneCallResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject constructor(private val weatherRepository: WeatherRepository) : BaseViewModelImpl(),
    WeatherContract.ViewModel {

    private val getWeatherOneCallResponseLiveData =
        MutableLiveData<Resource<WeatherOneCallResponse>>()

    override fun getWeatherOneCallData() {
        getWeatherOneCallResponseLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = weatherRepository.getWeatherOneCallData()
                viewModelScope.launch(Dispatchers.Main) {
                    getWeatherOneCallResponseLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception){
                getWeatherOneCallResponseLiveData.postValue(Resource.Error(e.message.orEmpty()))
            }
        }
    }


    override fun getWeatherOneCallLiveData(): LiveData<Resource<WeatherOneCallResponse>> =
        getWeatherOneCallResponseLiveData

}