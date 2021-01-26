package com.monebac.com.network


class LoginRepository {
    suspend fun login(map: MutableMap<String, String>) = NetworkService.apiService.getResult(map)
}