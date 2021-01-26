package com.monebac.com.wkyk.web

import android.view.KeyEvent
import android.webkit.WebSettings
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.monebac.com.R
import com.monebac.com.base.mBaseActivity
import kotlinx.android.synthetic.main.activity_agent_web.*
import kotlinx.android.synthetic.main.dialog_calendar.tv_title
import kotlinx.android.synthetic.main.layout_title.*

class AgentWebActivity : mBaseActivity() {
    lateinit var agentWeb: AgentWeb

    override fun initLayout(): Int {
        return R.layout.activity_agent_web
    }

    override fun initData() {
        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")
        tv_title.text = title

        agentWeb = AgentWeb.with(this).setAgentWebParent(frame, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
//                .setWebChromeClient()
//                .setWebViewClient()
                .createAgentWeb()
                .ready().go(url)
        val webView = agentWeb.webCreator.webView
        webView.settings.run {
            javaScriptEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
            //不显示缩放按钮
            displayZoomControls = false
            //设置自适应屏幕，两者合用
            //将图片调整到适合WebView的大小
            useWideViewPort = true
            //缩放至屏幕的大小
            loadWithOverviewMode = true
            //自适应屏幕
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        }

        back.setOnClickListener { finish() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}