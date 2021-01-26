package com.monebac.com.wkyk.ui.vm

import androidx.lifecycle.MutableLiveData
import com.monebac.com.base.BaseViewModel
import com.monebac.com.utils.fromJson
import com.monebac.com.wkyk.model.AcqData
import com.monebac.com.wkyk.model.ChannelModel

class YKchannelViewModel : BaseViewModel() {

    val ykchan = MutableLiveData<List<AcqData>>()
    val ykToast = MutableLiveData<String>()
    val ykUrl = MutableLiveData<String>()

    fun getYkChannel(map: MutableMap<String, String>) {
        launch(
                block = {
                    val result = resultRepository.getResult(map)
                    if (result.str39 == "00") {
                        val channelModel = fromJson<ChannelModel>(result.str57)
                        ykchan.value = channelModel.acqData
                    }
                }
        )
    }

    fun getChannelStatus(map: MutableMap<String, String>) {
        launch(
                block = {
                    val result = resultRepository.getResult(map)
                    if (result.str39 == "00") {
                        if (result.str38 == "00")
                            ykToast.value = "绑定成功"
                        else
                            ykUrl.value = result.str38
                    } else {
                        ykToast.value = result.str39
                    }
                }
        )
    }
}