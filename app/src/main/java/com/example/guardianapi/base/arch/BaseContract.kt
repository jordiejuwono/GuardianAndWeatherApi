package com.example.guardianapi.base.arch

interface BaseContract {
    interface View {
        fun observeData()
        fun showContent(isVisible: Boolean)
        fun showLoading(isVisible: Boolean)
        fun showError(isErrorEnabled: Boolean, msg: String? = null)
    }
    interface Repository {
        fun logResponse(msg: String?)
    }
    interface ViewModel {
        fun logResponse(msg: String?)
    }
}