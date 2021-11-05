package com.example.app.ui.base

import android.content.Context

interface BaseActivityController {
    fun showFragment(fragment: BaseFragment<*,*,*>)
    fun showFragment(fragment: BaseFragment<*,*,*>, addToBackStack: Boolean)
    fun getAppContext(): Context
}