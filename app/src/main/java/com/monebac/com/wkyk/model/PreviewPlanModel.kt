package com.monebac.com.wkyk.model

import java.io.Serializable

class PreviewPlanModel : Serializable {
    var HkMoney: String = ""
    var totoNum: String? = null
    var startTime: String? = null
    var endTime: String? = null
    var ZzjMoney: String = ""
    var fee: String = ""
    var timeFee: String = ""
    var totalFee: String? = null
    var address: MutableMap<String, AreaModel>? = null
    var industryStr: String = ""
    var AcqCode: String = ""
    var acqName: String? = null
    var rNum: String = ""
    var planDetail: String = ""
}
