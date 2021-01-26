package com.monebac.com.network

import com.monebac.com.base.BaseResult
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("request.app")
    suspend fun getResult(@FieldMap body: Map<String, String>): BaseResult
}