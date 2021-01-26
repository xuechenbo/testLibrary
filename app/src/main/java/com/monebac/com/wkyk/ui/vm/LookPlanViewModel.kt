package com.monebac.com.wkyk.ui.vm

import androidx.lifecycle.MutableLiveData
import com.monebac.com.base.BaseViewModel
import com.monebac.com.utils.getList
import com.monebac.com.wkyk.model.PlanAllModel

class LookPlanViewModel : BaseViewModel() {
    var lookup: MutableLiveData<List<PlanAllModel>> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
    }

    fun getListData(map: MutableMap<String, String>) {
        launch(
                block = {
                    val result = resultRepository.getResult(map)
                    if (result.str39 == "00") {
                        lookup.value = getList(result.str57)
                    }
                },
                error = {

                }
        )
    }

}