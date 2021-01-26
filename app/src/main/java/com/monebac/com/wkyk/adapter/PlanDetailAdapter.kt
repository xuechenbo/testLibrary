package com.monebac.com.wkyk.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.monebac.com.R
import com.monebac.com.utils.toYMD
import com.monebac.com.wkyk.model.PlanItemDetailModel

class PlanDetailAdapter(list: List<PlanItemDetailModel>) : BaseQuickAdapter<PlanItemDetailModel, BaseViewHolder>(R.layout.item_plan_detail, list) {

    private var mIsShowOnlyCount: Boolean = true
    private var mCount = 5//设置最多展示几条数据

    fun setShowOnlyItem(isShowOnlyThree: Boolean) {
        setShowOnlyCount(isShowOnlyThree)
    }


    //设置显示的条数
    private fun setShowOnlyCount(isShowOnlyThree: Boolean) {
        mIsShowOnlyCount = isShowOnlyThree
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (mIsShowOnlyCount) mCount else super.getItemCount()
    }


    override fun convert(helper: BaseViewHolder, item: PlanItemDetailModel) {

        helper.run {
            setText(R.id.type_tv, if (item.type == "sale") "消费" else "还款")
            setText(R.id.tv_date, item.planTime.time.toYMD())
            setText(R.id.tv_money, item.money)
            setText(R.id.tv_area, "消费地区:${item.customizeCity}")
            setText(R.id.industry, "消费商户:${item.industryName}")
            setGone(R.id.tv_area, item.type == "sale")
            setGone(R.id.industry, item.type == "sale")
            setText(R.id.tv_failRes, item.message)
            setGone(R.id.tv_failRes, item.status == "10C")
            setText(R.id.tv_planstatus, when (item.status) {
                "10A", "10D" -> "未执行"
                "10B" -> "完成"
                "10C" -> "失败"
                else -> "未知状态"
            })
        }
    }


}