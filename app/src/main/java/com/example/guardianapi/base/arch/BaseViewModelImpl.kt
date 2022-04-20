package com.example.guardianapi.base.arch

import android.util.Log
import androidx.lifecycle.ViewModel

open class BaseViewModelImpl: ViewModel(), BaseContract.ViewModel {
    override fun logResponse(msg: String?) {
        Log.d(BaseRepositoryImpl::class.java.simpleName, msg.orEmpty())
    }
}