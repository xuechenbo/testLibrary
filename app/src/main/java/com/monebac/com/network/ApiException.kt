package com.monebac.com.network

class ApiException(var code: Int, override var message: String) : RuntimeException()