package com.monebac.com.wkyk.ui.repository

import com.monebac.com.network.NetworkService

class ResultRepository {
    suspend fun getResult(map: MutableMap<String, String>) = NetworkService.apiService.getResult(map)
}