package com.monebac.com.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders

abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
//        mViewModel = ViewModelProvider(this)[viewModelClass()]
        mViewModel = ViewModelProviders.of(this).get(viewModelClass())
    }

    protected abstract fun viewModelClass(): Class<VM>

    abstract fun initView()

    abstract fun initData()

    open fun observe() {
//        mViewModel.loading.observe(this, Observer {
//            if (it) showProgressDialog(R.string.loading) else dismissProgressDialog()
//        })
    }

    protected fun setCustomToolBar(
            isVisibleLeftIcon: Boolean = false,
            toolbarTitle: String = ""
    ) {

    }
}
