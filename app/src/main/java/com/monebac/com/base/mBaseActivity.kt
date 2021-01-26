package com.monebac.com.base

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity

abstract class mBaseActivity : FragmentActivity() {
    lateinit var context: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(initLayout())
        //设置数据
        initData()
        //全局设置竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 设置布局
     *
     * @return
     */
    abstract fun initLayout(): Int

    /**
     * 设置数据
     */
    abstract fun initData()

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
