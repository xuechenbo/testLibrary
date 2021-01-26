package com.monebac.com.wkyk.model

data class ChannelModel(
        val acqData: List<AcqData>,
        val rescode: String
)

data class AcqData(
        val T0date: String,
        val acqcode: String,
        val channelName: String,
        val isluodi: String,
        val iszixuan: String,
        val limit: String,
        val noBank: String,
        val quota: String,
        val rate: String,
        val remark: String,
        val sort: String,
        val status: String
)