package com.monebac.com.wkyk.ui.vm

import androidx.lifecycle.MutableLiveData
import com.monebac.com.base.BaseViewModel
import com.monebac.com.utils.getList
import com.monebac.com.wkyk.model.PayItemModel

class PayRecordViewModel : BaseViewModel() {

    val mContent = MutableLiveData<List<PayItemModel>>()

    fun getContent(map: MutableMap<String, String>) {
        launch(
                block = {
                    val result = resultRepository.getResult(map)
                    if (result.str39 == "00") {
                        mContent.value = getList<List<PayItemModel>>(result.str32)
                    }
                },
                error = {

                }
        )
    }
}