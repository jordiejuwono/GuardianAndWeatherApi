package com.example.guardianapi.ui.webview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guardianapi.base.arch.BaseViewModelImpl
import com.example.guardianapi.base.model.Resource
import com.example.guardianapi.data.local.room.entity.Articles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel
    @Inject constructor(private val repository: WebViewRepository): BaseViewModelImpl(), WebViewContract.ViewModel {

    private val saveArticleLiveData = MutableLiveData<Resource<Long>>()

    override fun saveArticle(saveArticle: Articles) {
        saveArticleLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.saveArticle(saveArticle)
                viewModelScope.launch(Dispatchers.Main) {
                    saveArticleLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                saveArticleLiveData.postValue(Resource.Error(e.message.orEmpty()))
            }
        }
    }
}