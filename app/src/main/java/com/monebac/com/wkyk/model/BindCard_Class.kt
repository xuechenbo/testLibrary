package com.monebac.com.wkyk.model

class BindCard_Class : ArrayList<bindCardItem>()

data class bindCardItem(
        val BANK_ACCOUNT: String,
        val BANK_ACCOUNT_NAME: String,
        val BANK_NAME: String,
        val BANK_PHONE: String,
        val BILL_DAY: String,
        val CVN: String,
        val EXAMINE_RESULT: Any?,
        val EXPDATE: String,
        val ID: String,
        val ID_CARD_NUMBER: String,
        val INCREASE_LIMIT_STATUS: String,
        val LIMIT_MONEY: String,
        val REPAYMENT_DAY: String,
        val STATUS: String,
        val card_bit_code: Int,
        val completeCount: Int,
        val day: String,
        val plancount: Int,
        val short_cn_name: String,
        val type: Any?
)