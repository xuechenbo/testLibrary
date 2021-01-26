package com.monebac.com.wkyk.ui.act

import com.monebac.com.R
import com.monebac.com.base.BaseActivity
import com.monebac.com.utils.LoadBigImage
import com.monebac.com.utils.getCacheBitmapFromView
import kotlinx.android.synthetic.main.activity_show_image.*
import kotlinx.android.synthetic.main.layout_title.*

class ShowImageActivity : BaseActivity() {
    var strUrl = "http://47.112.35.227:80/image/friendsCircleImg/93563A6682B0486393B59909487A6EFA.png,http://47.112.35.227:80/image/friendsCircleImg/3B1C1A82D5654618AD2561118B18D667.png,http://47.112.35.227:80/image/friendsCircleImg/8AA291732183498B90BC1F2EFB9D4DA1.png"

    fun initData() {
        tv_title.text = "保存图片"
        back.setOnClickListener { finish() }
        val imageUrl = strUrl.split(",")[0]
        LoadBigImage(this, imageUrl, iv)

        iv.setOnLongClickListener {
            val cacheBitmapFromView = iv.getCacheBitmapFromView()

            true
        }
    }

    override fun layoutRes(): Int {
        return R.layout.activity_show_image
    }
}