package com.example.app.ui.base

import android.content.Context
import io.reactivex.rxjava3.disposables.Disposable

interface BaseContract {
    interface Presenter<V> {
        fun getMvpView(): V
        fun addDisposable(disposable: Disposable)
        fun onAttach()
        fun onDetach()
    }

    interface View {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showMessage(message: String)
        fun getAppContext(): Context
        fun hideKeyboard(view: android.view.View)
    }
}