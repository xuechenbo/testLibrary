package com.monebac.com.wkyk

import java.security.MessageDigest

class Constant {
    companion object {
        val mainKey = "21E4ACD4CD5D4619B063F40C5A454F7D"
        val BASE_IP = "http://chengxinshenghuo.llyzf.cn"
        val BASE_URL = "$BASE_IP:80"
        val REQUEST_API = "$BASE_URL/lly-posp-proxy/request.app?"
        val RETROFIT_URL = "$BASE_IP:80/lly-posp-proxy/"

        val VERSION = "CXSH-A-1.0.1"

//

        fun Md5(str: String): String {
            val digest = MessageDigest.getInstance("MD5")
            val result = digest.digest(str.toByteArray())
            //没转16进制之前是16位
            println("result${result.size}")
            //转成16进制后是32字节
            return toHex(result)
        }

        fun toHex(byteArray: ByteArray): String {
            val result = with(StringBuilder()) {
                byteArray.forEach {
                    val hex = it.toInt() and (0xFF)
                    val hexStr = Integer.toHexString(hex)
                    if (hexStr.length == 1) {
                        this.append("0").append(hexStr)
                    } else {
                        this.append(hexStr)
                    }
                }
                this.toString()
            }
            //转成16进制后是32字节
            return result
        }
    }


}