package com.monebac.com.wkyk.model

import com.monebac.com.utils.getTimeCurr
import java.io.Serializable

class PlanItemModel : Serializable {
    val acqCode: String? = null
    val bindID: String? = null
    val cardNo: String? = null
    val cityId: String? = null
    val cityIndustry: String? = null
    val cityName: String? = null
    val groupNum: String? = null
    var industryName: String? = null
    val money: String? = null
    val status: String? = null
    val time: String? = null
    val type: String? = null
    var customizecity: String? = null


    override fun toString(): String = "{" +
            "planTime=${time!!.getTimeCurr()}," +
            "fromCard='$cardNo'," +
            "toCard='$cardNo'," +
            "fromBindId='$bindID'," +
            "toBindId='$bindID'," +
            "fromMoney=$money," +
            "toMoney=$money," +
            "payFee='null'," +
            "status='$status'," +
            "type='$type'," +
            "groupNum='$groupNum'," +
            "customizecity='$customizecity'," +
            "cityindustry='$cityIndustry'," +
            "cityindustryName='$industryName'," +
            "acqCode='$acqCode'" +
            "}"
}