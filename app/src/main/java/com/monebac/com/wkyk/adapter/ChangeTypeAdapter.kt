package com.monebac.com.wkyk.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.monebac.com.R

class ChangeTypeAdapter(res: Int, list: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(res, list) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_text, item)
    }
}