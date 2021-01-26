package com.monebac.com.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.view.View
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException

fun Context.readBitMap(resId: Int): Bitmap? {
    val opt = BitmapFactory.Options()
    opt.inPreferredConfig = Bitmap.Config.RGB_565
    opt.inPurgeable = true
    opt.inInputShareable = true
    //获取资源图片
    val iv = resources.openRawResource(resId)
    return BitmapFactory.decodeStream(iv, null, opt)
}

// 根据Path转Bitmap
fun String.toBitmapHeightAndWidth(height: Float, width: Float): Bitmap {
    val newOpts = BitmapFactory.Options()
    // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
    newOpts.inJustDecodeBounds = true
    var bitmap = BitmapFactory.decodeFile(this, newOpts)// 此时返回bm为空

    newOpts.inJustDecodeBounds = false
    val w = newOpts.outWidth
    val h = newOpts.outHeight
    // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
    // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
    var be = 1// be=1表示不缩放
    if (w > h && w > width) {// 如果宽度大的话根据宽度固定大小缩放
        be = (newOpts.outWidth / width).toInt()
    } else if (w < h && h > height) {// 如果高度高的话根据宽度固定大小缩放
        be = (newOpts.outHeight / height).toInt()
    }
    if (be <= 0)
        be = 1
    newOpts.inSampleSize = be// 设置缩放比例
    // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
    bitmap = BitmapFactory.decodeFile(this, newOpts)
    return bitmap// 压缩好比例大小后再进行质量压缩
}

/**
 * 获取一个 View 的缓存视图
 * @param view
 * @return
 */
fun View.getCacheBitmapFromView(): Bitmap? {
    val drawingCacheEnabled = true
    isDrawingCacheEnabled = drawingCacheEnabled
    buildDrawingCache(drawingCacheEnabled)
    val drawingCache = drawingCache
    val bitmap: Bitmap?
    if (drawingCache != null) {
        bitmap = Bitmap.createBitmap(drawingCache)
        isDrawingCacheEnabled = false
    } else {
        bitmap = null
    }
    return bitmap
}

// 图片质量压缩
fun Bitmap.imgQualityCompression(srcPath: String?): Bitmap {
    val mByteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, mByteArrayOutputStream)// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
    var options = 100
    while (mByteArrayOutputStream.toByteArray().size / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        mByteArrayOutputStream.reset()// 重置baos即清空baos
        compress(Bitmap.CompressFormat.JPEG, options, mByteArrayOutputStream)// 这里压缩options%，把压缩后的数据存放到baos中
        options -= 10// 每次都减少10
    }
    val isBm = ByteArrayInputStream(mByteArrayOutputStream.toByteArray())// 把压缩后的数据baos存放到ByteArrayInputStream中
    val bitmap = BitmapFactory.decodeStream(isBm, null, null)// 把ByteArrayInputStream数据生成图片
    if (!TextUtils.isEmpty(srcPath)) {
        try {
            val out = FileOutputStream(srcPath)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 90, out)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    return bitmap!!
}

//Bitmap转Base64String
fun Bitmap.toBase64String(): String? {
    var result: String? = null
    var mByteArrayOutputStream: ByteArrayOutputStream? = null
    try {
        mByteArrayOutputStream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 100, mByteArrayOutputStream)
        mByteArrayOutputStream.flush()
        mByteArrayOutputStream.close()

        val bitmapBytes = mByteArrayOutputStream.toByteArray()
        result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            if (mByteArrayOutputStream != null) {
                mByteArrayOutputStream.flush()
                mByteArrayOutputStream.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    return result
}

fun Activity.imageUrlPathGetStringPath(imgUrl: Uri): String {
    val imgs = arrayOf(MediaStore.Images.Media.DATA)//将图片URI转换成存储路径
    val cursor = managedQuery(imgUrl, imgs, null, null, null)
    val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    return cursor.getString(index)
}


// 图片质量压缩+网络压缩
fun Context.saveImg(mBitmap: Bitmap, srcPath: String?) {
    val mByteArrayOutputStream = ByteArrayOutputStream()
    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, mByteArrayOutputStream)// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
    val isBm = ByteArrayInputStream(mByteArrayOutputStream.toByteArray())// 把压缩后的数据baos存放到ByteArrayInputStream中
    val bitmap = BitmapFactory.decodeStream(isBm, null, null)// 把ByteArrayInputStream数据生成图片
    if (!TextUtils.isEmpty(srcPath)) {
        try {
            val out = FileOutputStream(srcPath)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 90, out)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val files: MutableList<String> = arrayListOf(srcPath!!)
//        synchronousLuban(files, File(srcPath).name)
    }
}
