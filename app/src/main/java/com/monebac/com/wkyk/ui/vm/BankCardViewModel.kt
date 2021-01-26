package com.monebac.com.wkyk.ui.vm

import androidx.lifecycle.MutableLiveData
import com.monebac.com.base.BaseViewModel
import com.monebac.com.utils.getList
import com.monebac.com.wkyk.model.BindCard

class BankCardViewModel : BaseViewModel() {
    override fun onCleared() {
        super.onCleared()
    }

    val bankList = MutableLiveData<List<BindCard>>()

    fun getBankList(map: MutableMap<String, String>) {
        launch(
                block = {
                    loading.value = true
                    val result = resultRepository.getResult(map)
                    if (result.str39 == "00") {
                        bankList.value = getList(result.str57)
                    }
                    loading.value = false
                }
        )
    }

}