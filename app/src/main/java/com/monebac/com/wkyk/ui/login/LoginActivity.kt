package com.monebac.com.wkyk.ui.login

import android.content.Intent
import androidx.lifecycle.Observer
import com.monebac.com.R
import com.monebac.com.base.BaseVmActivity
import com.monebac.com.utils.PreferencesUtil
import com.monebac.com.utils.getMap
import com.monebac.com.wkyk.Constant
import com.monebac.com.wkyk.ui.act.BankCardListActivity
import com.monebac.com.wkyk.web.AgentWebActivity
import com.umeng.socialize.UMShareAPI
import kotlinx.android.synthetic.main.activity_new_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseVmActivity<LoginViewModel>() {
    override fun layoutRes(): Int = R.layout.activity_new_login

    override fun viewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun initView() {
        val phone = PreferencesUtil.getString("phone")
        val pwd = PreferencesUtil.getString("pwd")
        if (phone.isNotEmpty() || pwd.isNotEmpty()) {
            et_phone.setText(phone)
            et_pass.setText(pwd)
        }
    }

    override fun initData() {
        btn_login.setOnClickListener {
            when {
                et_phone.text.isNullOrEmpty() -> toast("手机号为空")
                et_pass.text.isNullOrEmpty() -> toast("密码为空")
                else -> mViewModel.login(
                        getMap(mutableMapOf(
                                "1" to et_phone.text.toString(),
                                "3" to "190928",
                                "8" to Constant.Md5(et_pass.text.toString())))
                )
            }
        }
        tv_start.setOnClickListener {
        }
        tv_forget_pass.setOnClickListener {
            startActivity<AgentWebActivity>("title" to "忘记密码", "url" to "https://www.zhihu.com")
        }
        tv_jetpack.setOnClickListener {

        }
    }


    override fun observe() {
        super.observe()
        mViewModel.run {
            loginMsg.observe(this@LoginActivity, Observer {
                if (it == "00") {
                    PreferencesUtil.saveValue("phone", et_phone.text.toString())
                    PreferencesUtil.saveValue("pwd", et_pass.text.toString())
                    startActivity<BankCardListActivity>()
                    finish()
                } else {
                    toast(it)
                }
            })
            loading.observe(this@LoginActivity, Observer {
                if (it) showProgressDialog(R.string.loading) else dismissProgressDialog()
            })
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}