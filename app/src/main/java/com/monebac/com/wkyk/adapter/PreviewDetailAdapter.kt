package com.monebac.com.wkyk.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.monebac.com.R
import com.monebac.com.wkyk.model.PlanItemModel

class PreviewDetailAdapter(res: Int, data: List<PlanItemModel>) : BaseQuickAdapter<PlanItemModel, BaseViewHolder>(res, data) {
    override fun convert(helper: BaseViewHolder, item: PlanItemModel) {
        helper.run {
            setText(R.id.type_tv, if (item.type == "sale") "消费" else "还款")
            setText(R.id.tv_date, item.time)
            setText(R.id.tv_money, item.money)
            setText(R.id.tv_area, "消费地区:${item.customizecity}")
            setText(R.id.industry, "消费商户:${item.industryName}")
            setGone(R.id.tv_area, item.type == "sale")
            setGone(R.id.industry, item.type == "sale")
        }
    }
}