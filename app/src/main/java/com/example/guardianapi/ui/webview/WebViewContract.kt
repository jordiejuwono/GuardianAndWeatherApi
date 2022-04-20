package com.example.guardianapi.ui.webview

import com.example.guardianapi.base.arch.BaseContract
import com.example.guardianapi.data.local.room.entity.Articles

interface WebViewContract {
    interface View : BaseContract.View {

    }
    interface Repository : BaseContract.Repository {
        suspend fun saveArticle(saveArticle: Articles) : Long
    }
    interface ViewModel : BaseContract.ViewModel {
        fun saveArticle(saveArticle: Articles)
    }
}