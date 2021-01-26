package com.monebac.com.wkyk.ui.vm

import androidx.lifecycle.MutableLiveData
import com.monebac.com.base.BaseViewModel
import com.monebac.com.wkyk.model.PreviewPlanModel
import java.math.BigDecimal

class MakePlanViewModel : BaseViewModel() {
    val plamMsg = MutableLiveData<PreviewPlanModel>()
    val mLoading = MutableLiveData<Boolean>()

    fun getPlanMsg(map: MutableMap<String, String>) {
        launch(
                block = {
                    mLoading.value = true
                    val result = resultRepository.getResult(map)
                    if (result.str39 == "00") {
                        plamMsg.value = PreviewPlanModel().apply {
                            HkMoney = result.str8
                            val split = result.str10.split(",")
                            startTime = split[0]
                            endTime = split[split.size - 1]
                            ZzjMoney = result.str40
                            fee = result.str9
                            timeFee = result.str7
                            totoNum = result.str20
                            totalFee = BigDecimal(result.str40).add(BigDecimal(result.str9)).add(BigDecimal(result.str7)).toString()
//                            address = selectAreaMap
//                            industryStr = Industry
//                            AcqCode = acqCode
//                            acqName = intent.getStringExtra("acqName")
//                            rNum = dailyNum
                            planDetail = result.str57
                        }
                    }
                    mLoading.value = false
                }
        )
    }
}