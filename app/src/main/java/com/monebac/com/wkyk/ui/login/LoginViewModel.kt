package com.monebac.com.wkyk.ui.login
import androidx.lifecycle.MutableLiveData
import com.monebac.com.network.LoginRepository
import com.monebac.com.base.BaseViewModel
import com.monebac.com.utils.LogsUtils
import com.monebac.com.utils.PreferencesUtil
import com.monebac.com.utils.fromJson
import com.monebac.com.wkyk.model.UserInfoModel

class LoginViewModel : BaseViewModel() {
    override fun onCleared() {
        super.onCleared()
    }

    private val loginRepository by lazy {
        LoginRepository()
    }
    val loginMsg = MutableLiveData<String>()

    fun login(map: MutableMap<String, String>) {
        launch(
                block = {
                    loading.value = true
                    val result = loginRepository.login(map)
                    if (result.str39 == "00") {
                        val fromJson = fromJson<UserInfoModel>(result.str42)
                        val userInfoModelItem = fromJson?.get(0)
                        LogsUtils.e(userInfoModelItem!!.merchantNo)

                        PreferencesUtil.saveValue("merNo", userInfoModelItem.merchantNo)
                        PreferencesUtil.saveValue("merId", userInfoModelItem.id)
                    }
                    loginMsg.value = result.str39
                    loading.value = false
                },
                error = {
                    loading.value = false
                }
        )
    }

}