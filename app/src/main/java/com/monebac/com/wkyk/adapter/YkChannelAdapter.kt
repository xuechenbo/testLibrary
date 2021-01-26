package com.monebac.com.wkyk.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.monebac.com.R
import com.monebac.com.wkyk.model.AcqData

class YkChannelAdapter(res: Int, data: List<AcqData>) : BaseQuickAdapter<AcqData, BaseViewHolder>(res, data) {
    override fun convert(helper: BaseViewHolder, item: AcqData) {
        helper.setText(R.id.item_content, item.remark)
                .setText(R.id.tv_dealRate, item.rate)
                .setText(R.id.tv_channelName, item.channelName)
                .setText(R.id.tv_limitPerTimes, "单笔限额:${item.limit}")
                .setText(R.id.tv_dealTimes, "交易时间:${item.T0date}")
                .addOnClickListener(R.id.bt_chose)
                .addOnClickListener(R.id.tv_remark)

    }
}