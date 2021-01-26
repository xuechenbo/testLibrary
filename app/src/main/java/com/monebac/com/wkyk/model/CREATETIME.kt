package com.monebac.com.wkyk.model

import java.io.Serializable

data class CREATETIME(
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
) : Serializable