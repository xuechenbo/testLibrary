package com.monebac.com.wkyk.ui.act

import androidx.lifecycle.Observer
import com.monebac.com.R
import com.monebac.com.base.BaseVmActivity
import com.monebac.com.utils.PreferencesUtil
import com.monebac.com.utils.getMap
import com.monebac.com.wkyk.adapter.YkChannelAdapter
import com.monebac.com.wkyk.event.ClosePlanEvent
import com.monebac.com.wkyk.model.AcqData
import com.monebac.com.wkyk.model.BindCard
import com.monebac.com.wkyk.ui.vm.YKchannelViewModel
import com.monebac.com.wkyk.web.AgentWebActivity
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_bank_list.*
import kotlinx.android.synthetic.main.layout_title.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class YKchannelActivity : BaseVmActivity<YKchannelViewModel>() {

    lateinit var ykChannelAdapter: YkChannelAdapter
    private lateinit var mList: List<AcqData>
    private var bindCard: BindCard? = null

    override fun viewModelClass(): Class<YKchannelViewModel> = YKchannelViewModel::class.java

    override fun layoutRes(): Int = R.layout.act_ykchannel

    override fun initView() {
        EventBus.getDefault().register(this)
        mList = ArrayList()
        bindCard = intent.getSerializableExtra("BindCard_Class") as BindCard
        tv_title.text = "通道列表"
    }

    override fun initData() {
        back.setOnClickListener { finish() }
        ykChannelAdapter = YkChannelAdapter(R.layout.item_yk_channel, mList).also {
            it.setOnItemChildClickListener { adapter, view, position ->
                val acqData = adapter.data[position] as AcqData
                when (view.id) {
                    R.id.bt_chose ->
                        if (acqData.status == "未开通")
                            mViewModel.getChannelStatus(initCard(acqData.acqcode))
                        else
                            startActivity<MakePlanDesActivity>(
                                    "BindCard_Class" to bindCard,
                                    "acqCode" to acqData.acqcode,
                                    "acqName" to acqData.channelName)
                    R.id.tv_remark ->
                        startActivity<ShowImageActivity>()
                }
            }
            recyCler.adapter = it
        }
        smarFresh.run {
            refreshHeader = ClassicsHeader(context)
            isEnableLoadmore = false
            setOnRefreshListener {
                mViewModel.getYkChannel(getMap(mutableMapOf(
                        "3" to "390013",
                        "43" to "YK",
                        "44" to bindCard!!.ID
                )))
            }
        }
        smarFresh.autoRefresh()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            ykchan.observe(this@YKchannelActivity, Observer {
                smarFresh.finishRefresh()
                ykChannelAdapter.setNewData(it)
            })
            ykToast.observe(this@YKchannelActivity, Observer {
                toast(it)
            })
            ykUrl.observe(this@YKchannelActivity, Observer {
                startActivity<AgentWebActivity>("url" to it, "title" to "通道帮卡")
            })
        }
    }

    private fun initCard(acqcode: String): MutableMap<String, String> =
            getMap(mutableMapOf(
                    "3" to "490008"
                    , "5" to bindCard!!.LIMIT_MONEY
                    , "6" to bindCard!!.BILL_DAY
                    , "7" to bindCard!!.REPAYMENT_DAY
                    , "37" to bindCard!!.ID
                    , "42" to bindCard!!.ID_CARD_NUMBER
                    , "43" to PreferencesUtil.getString("merId")
                    , "45" to bindCard!!.BANK_ACCOUNT
                    , "46" to bindCard!!.BANK_PHONE
                    , "47" to bindCard!!.EXPDATE
                    , "48" to bindCard!!.CVN
                    , "49" to acqcode
                    , "31" to ""
                    , "41" to ""
            ), "1")


    @Subscribe
    fun onEvent(event: ClosePlanEvent) {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}