package com.monebac.com.wkyk.web

import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.LinearLayout
import com.google.gson.Gson
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.PermissionInterceptor
import com.just.agentweb.WebChromeClient
import com.monebac.com.R
import com.monebac.com.base.mBaseActivity
import kotlinx.android.synthetic.main.activity_agent_web.*
import kotlinx.android.synthetic.main.dialog_calendar.tv_title
import kotlinx.android.synthetic.main.layout_title.*
import org.jetbrains.anko.toast


open class AgentTestActivity : mBaseActivity() {
    lateinit var agentWeb: AgentWeb
    val strUrl = "file:///android_asset/js_interaction/hello.html"
    val fileUrl = "file:///android_asset/upload_file/uploadfile.html"
    private val mGson = Gson()
    override fun initLayout(): Int {
        return R.layout.activity_agent_web
    }

    override fun initData() {
        val title = "Agentweb"
        tv_title.text = title

        agentWeb = AgentWeb.with(this).setAgentWebParent(frame, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(-1, 3)
                .setWebChromeClient(CommonWebChromeClient()) //WebChromeClient
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .go(fileUrl)
        agentWeb.jsInterfaceHolder.addJavaObject("android", AndroidJavascriptInterface())

        initListener()
    }

    private fun initListener() {
        back.setOnClickListener {
            agentWeb.jsAccessEntrace.quickCallJs("callByAndroidInteraction", "你好Js")
        }
    }

    protected var mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            Log.i("mWebChromeClient", "onProgressChanged:$newProgress  view:$view")
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            var title = title
            super.onReceivedTitle(view, title)
            if (tv_title != null && !TextUtils.isEmpty(title)) {
                if (title.length > 10) {
                    title = title.substring(0, 10) + "..."
                }
            }
            tv_title.text = title
        }
    }


    private inner class AndroidJavascriptInterface {
        @JavascriptInterface
        fun callAndroid(msg: String) {
            toast("$msg")
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    protected var mPermissionInterceptor = PermissionInterceptor { url, permissions, action ->

        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        Log.i("拦截", "mUrl:" + url + "  permission:" + mGson.toJson(permissions) + " action:" + action)
        false
    }
}