package com.example.guardianapi.ui.mainfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guardianapi.base.arch.BaseViewModelImpl
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.network.model.guardianapi.GuardianApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel
@Inject constructor(private val mainFragmentRepository: MainFragmentRepository) : BaseViewModelImpl(),
    MainFragmentContract.ViewModel {

    private val getArticleListResponseLiveData = MutableLiveData<Resource<GuardianApiResponse>>()

    override fun getArticleList(currentPage: Int) {
        getArticleListResponseLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainFragmentRepository.getArticleList(currentPage)
                viewModelScope.launch(Dispatchers.Main) {
                    getArticleListResponseLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                getArticleListResponseLiveData.value = Resource.Error(e.message.orEmpty())
            }
        }
    }

    override fun getArticleListLiveData(): LiveData<Resource<GuardianApiResponse>> =
        getArticleListResponseLiveData
}