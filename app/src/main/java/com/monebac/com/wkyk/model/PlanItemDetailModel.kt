package com.monebac.com.wkyk.model

data class PlanItemDetailModel(
        val acqCode: String,
        val acqTraceNo: String,
        val cityIndustry: String,
        val customizeCity: String,
        val fromIncreaseId: String,
        val groupNumber: String,
        val id: String,
        val industryName: String,
        val merchantId: String,
        val message: String,
        val money: String,
        val number: String,
        val orderId: String,
        val paymentOrderId: String,
        val planId: String,
        val planTime: PlanTime,
        val pro: String,
        val rate: String,
        val status: String,
        val toIncreaseId: String,
        val toMoney: String,
        val type: String
)

data class PlanTime(
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