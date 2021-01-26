package com.monebac.com.wkyk.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.monebac.com.R
import com.monebac.com.wkyk.model.BindCard

class BindCardListAdapter(layoutResId: Int, data: List<BindCard>) : BaseQuickAdapter<BindCard, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: BindCard) {
        helper.setText(R.id.tv_bank_name, item.short_cn_name)
                .setText(R.id.tv_bank_account, item.BANK_ACCOUNT)
                .setText(R.id.tv_bill_day, item.BILL_DAY)
                .setText(R.id.tv_pay_day, item.REPAYMENT_DAY)
                .setText(R.id.tv_limit, item.LIMIT_MONEY)
                .setText(R.id.tv_dead_day, "5天到期")
                .addOnClickListener(R.id.tv_plan)
                .addOnClickListener(R.id.tv_look_plan)
    }
}
