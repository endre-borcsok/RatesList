package com.example.app.ui.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.app.R
import com.example.app.common.ForexSessionContract
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<VB: ViewBinding,
        A: BaseActivityController,
        P: BaseContract.Presenter<*>>
    : Fragment(), BaseContract.View {

    @Inject
    lateinit var forexSession: ForexSessionContract

    @Inject
    lateinit var presenter: P

    @Inject
    lateinit var binding: VB

    private lateinit var loadingAlertDialog: AlertDialog

    private lateinit var activityController: A

    abstract fun getTitle(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        activityController =  context as A
        loadingAlertDialog = AlertDialog.Builder(context).create().apply {
            setTitle(getString(R.string.loading))
            setCancelable(false)
        }
        presenter.onAttach()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setTitle(getTitle())
    }

    override fun onDetach() {
        super.onDetach()
        presenter.onDetach()
    }

    override fun showLoadingDialog() {
        activity?.runOnUiThread { loadingAlertDialog.show() }
    }

    override fun hideLoadingDialog() {
        activity?.runOnUiThread { loadingAlertDialog.hide() }
    }

    override fun showMessage(message: String) {
        activity?.runOnUiThread {
            AlertDialog.Builder(context).apply {
                setTitle(message)
                    .setNegativeButton(getString(R.string.cancel)
                    ) { dialog, _ ->
                        dialog.dismiss()
                    }
                setCancelable(true)
            }.create().show()
        }
    }

    override fun getAppContext(): Context {
        return getActivityController().getAppContext()
    }

    override fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @JvmName("getMvpPresenter")
    fun getPresenter(): P {
        return presenter
    }

    fun getActivityController(): A {
        return activityController
    }
}