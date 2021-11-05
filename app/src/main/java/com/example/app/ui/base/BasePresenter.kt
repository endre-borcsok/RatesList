package com.example.app.ui.base

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter<V : BaseContract.View>(private val view: V)
    : BaseContract.Presenter<V> {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun getMvpView() = view

    override fun onAttach() {}

    override fun onDetach() {
        compositeDisposable.dispose()
    }
}