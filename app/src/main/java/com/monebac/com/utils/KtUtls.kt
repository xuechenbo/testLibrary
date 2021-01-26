package com.monebac.com.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.monebac.com.R
import com.monebac.com.wkyk.Constant
import com.monebac.com.wkyk.Constant.Companion.mainKey
import java.text.SimpleDateFormat
import java.util.*


//TODO 扩展函数
//时间戳转日期字符串
fun Long.toYMD(): String = SimpleDateFormat("yyyy-MM-dd").format(this)


//日期字符串转时间戳
fun String.getTimeCurr(): Long = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this).time

fun String.getTimeCurrYMD(): Long = SimpleDateFormat("yyyy-MM-dd").parse(this).time


inline fun <reified T : Any> getList(json: String): T {
    return Gson().fromJson(json, object : TypeToken<T>() {}.type)
}

//TODO 关键字 inline内联函数
// 不在传入 T 的class，inline 的作用就是将函数插入到被调用处，配合 reified 就可以获取到 T 真正的类型
inline fun <reified T : Any> fromJson(json: String): T {
    return Gson().fromJson(json, T::class.java)
}

fun LoadBigImage(context: Context, url: String, iv: ImageView) {
    context?.run {
        Glide.with(this).load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.main_logo)//图片加载中
                .error(R.drawable.main_logo)//图片加载失败
                .into(iv)
    }
}

fun getMap(map: MutableMap<String, String>, strNum: String = "42"): MutableMap<String, String> {
    map["0"] = "0700"
    map[strNum] = PreferencesUtil.getString("merNo")
    map["59"] = Constant.VERSION
    map["64"] = getMacByHashMap(map)
    return map
}

/**
 * 根据参数获取签名加密
 *
 * @param map
 * @return
 */
fun getMacByHashMap(map: Map<String, String>): String {
    val intMap = mutableMapOf<Any, Any>()
    for (str in map.keys) {
        map[str]?.let { intMap.put(Integer.valueOf(str), it) }
    }
    val sb = StringBuffer()
    val values = intMap.keys.toTypedArray()
    Arrays.sort(values)
    for (keyId in values) {
        if (intMap[keyId] == null) continue
        sb.append(intMap[keyId])
    }
    return Constant.Md5(sb.toString() + mainKey)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}












