package com.monebac.com.wkyk.ui.vm

import androidx.lifecycle.MutableLiveData
import com.monebac.com.base.BaseViewModel

class PrevDetailViewModel : BaseViewModel() {

    val succType = MutableLiveData<String>()

    fun subPlan(map: MutableMap<String, String>) {
        launch(
                block = {
                    val result = resultRepository.getResult(map)
                    if (result.str39 == "00") {
                        succType.value = "1"
                    }
                }
        )
    }
}