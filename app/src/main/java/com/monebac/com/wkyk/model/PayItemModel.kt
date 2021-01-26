package com.monebac.com.wkyk.model

import com.chad.library.adapter.base.entity.MultiItemEntity

data class PayItemModel(
        val NAME: String,
        val money: String,
        val orderMoney: String,
        val phone: String,
        val remark: String,
        val time: Time,
        val type: String
) : MultiItemEntity {
    override fun getItemType(): Int {
        return when (type) {
            "10A", "10M" -> 0
            "10F" -> 1
            else -> 2
        }
    }
}

data class Time(
        val date: Int,
        val day: Int,
        val hours: Int,
        val minutes: Int,
        val month: Int,
        val nanos: Int,
        val seconds: Int,
        val time: Long,
        val timezoneOffset: Int,
        val year: Int
)