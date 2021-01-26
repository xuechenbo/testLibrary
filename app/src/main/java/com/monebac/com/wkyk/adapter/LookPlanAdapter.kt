package com.monebac.com.wkyk.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.monebac.com.R
import com.monebac.com.utils.toYMD
import com.monebac.com.wkyk.model.PlanAllModel

class LookPlanAdapter(list: List<PlanAllModel>) : BaseQuickAdapter<PlanAllModel, BaseViewHolder>(R.layout.item_look_plan, list) {

    override fun convert(helper: BaseViewHolder, item: PlanAllModel) {

        helper?.run {
            setText(R.id.tv_planCreatTime, "创建时间:${item.CREATE_TIME.time.toYMD()}")
            setText(R.id.tv_planStatus, when (item.STATUS) {
                "10A" -> "未执行"
                "10B" -> "执行中"
                "10D" -> "已结束"
                "10C" -> "失败/暂停"
                else -> "已完成"
            })
            setText(R.id.tv_planDay, "计划天数:${item.START_TIME.time.toYMD()} 至 ${item.END_TIME.time.toYMD()}")
            setText(R.id.tv_shouldMoney, "本期应还:${item.PLAN_AMT}")
            setText(R.id.tv_paidMoney, "本期已还:${item.payed}")
            addOnClickListener(R.id.btn_detail)

            setText(R.id.tv_progressNum, item.progress)
            setProgress(R.id.progress, item.progress.replaceFirst("%", "").toInt())


        }
    }
}