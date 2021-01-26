package com.monebac.com.wkyk.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.monebac.com.R
import com.monebac.com.wkyk.model.PayItemModel


//TODO 多条目布局  PayItemModel 继承MultiItemEntity
class PayRecordAdapter(data: List<PayItemModel>) : BaseMultiItemQuickAdapter<PayItemModel, BaseViewHolder>(data) {

    init {
        addItemType(0, R.layout.item_pay_record1)
        addItemType(1, R.layout.item_pay_record2)
        addItemType(2, R.layout.item_pay_record3)
    }

    override fun convert(helper: BaseViewHolder, item: PayItemModel) {
        when (helper.itemViewType) {
            0 -> {

            }
            1 -> {

            }
            else -> {

            }
        }
    }


}