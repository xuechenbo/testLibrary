package com.monebac.com.wkyk.ui.vm

import androidx.lifecycle.MutableLiveData
import com.monebac.com.base.BaseViewModel
import com.monebac.com.utils.getList
import com.monebac.com.wkyk.model.PlanItemDetailModel

class PlanDetailViewModel : BaseViewModel() {
    val detail = MutableLiveData<List<PlanItemDetailModel>>()

    fun getDetailPlan(map: MutableMap<String, String>) {
        launch(
                block = {
                    val result = resultRepository.getResult(map)
                    if (result.str39 == "00") {
                        detail.value = getList(result.str57)
                    }
                }
        )

    }
}