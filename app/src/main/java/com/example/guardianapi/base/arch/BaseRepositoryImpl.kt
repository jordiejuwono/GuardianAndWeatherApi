package com.example.guardianapi.base.arch

import android.util.Log

open class BaseRepositoryImpl: BaseContract.Repository {
    override fun logResponse(msg: String?) {
        Log.d(BaseRepositoryImpl::class.java.simpleName, msg.orEmpty())
    }
}