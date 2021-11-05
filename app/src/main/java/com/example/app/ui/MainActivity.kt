package com.example.app.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.app.R
import com.example.app.databinding.ActivityMainBinding
import com.example.app.ui.base.BaseFragment
import com.example.app.ui.rateslist.RatesListFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityController {

    @Inject
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) showFragment(RatesListFragment(), false)
    }

    override fun showFragment(fragment: BaseFragment<*,*,*>) {
        showFragment(fragment, true)
    }

    override fun showFragment(fragment: BaseFragment<*,*,*>, addToBackStack: Boolean) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment, null)
            setReorderingAllowed(true)
            if (addToBackStack) addToBackStack(null)
        }
    }

    override fun getAppContext(): Context {
        return applicationContext
    }
}