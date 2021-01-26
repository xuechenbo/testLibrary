package com.monebac.com.wkyk.ui.act

import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.monebac.com.R
import com.monebac.com.base.BaseVmActivity
import com.monebac.com.utils.getMap
import com.monebac.com.wkyk.adapter.BindCardListAdapter
import com.monebac.com.wkyk.model.BindCard
import com.monebac.com.wkyk.ui.vm.BankCardViewModel
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.act_change_area.*
import kotlinx.android.synthetic.main.layout_title.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import kotlin.system.exitProcess

class BankCardListActivity : BaseVmActivity<BankCardViewModel>() {

    var mList = ArrayList<BindCard>()

    private lateinit var bindCardAdapter: BindCardListAdapter

    override fun viewModelClass(): Class<BankCardViewModel> = BankCardViewModel::class.java

    override fun layoutRes(): Int = R.layout.activity_bank_list

    override fun initView() {
        back.visibility = View.GONE
        tv_title.text = "信用卡列表"
        other.visibility = View.VISIBLE
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            bankList.observe(this@BankCardListActivity, Observer {
                smarFresh.finishRefresh()
                bindCardAdapter.setNewData(it)
            })
            loading.observe(this@BankCardListActivity, Observer {
//                if (it) showProgressDialog(R.string.loading) else dismissProgressDialog()
            })
        }
    }


    override fun initData() {
        smarFresh.autoRefresh()
        recyCler.run {
            layoutManager = LinearLayoutManager(context)
            bindCardAdapter = BindCardListAdapter(R.layout.item_bank_card, mList)
            adapter = bindCardAdapter
        }

        other.setOnClickListener {
            startActivity<PayRecordListActivity>()
        }
        smarFresh.run {
            isEnableLoadmore = false
            refreshHeader = ClassicsHeader(context)
            setOnRefreshListener {
                mViewModel.getBankList(map)
            }
        }

        bindCardAdapter.run {
            setOnItemChildClickListener { adapter, view, postion ->
                val data = adapter.data[postion] as BindCard
                when (view.id) {
                    R.id.tv_plan -> {
                        if (data.plancount == 0)
                            startActivity<YKchannelActivity>("BindCard_Class" to data)
                        else
                            toast("有计划执行中...")
                    }
                    R.id.tv_look_plan -> startActivity<LookPlanActivity>("BindCard_Class" to data)
                }
            }
            setOnItemClickListener { _, _, _ ->

            }
        }
    }

    private var firstime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val secondtime = System.currentTimeMillis()
            if (secondtime - firstime > 3000) {
                toast("再按一次返回键退出")
                firstime = System.currentTimeMillis()
                return true
            } else {
                exitProcess(0)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        var map = getMap(mutableMapOf("3" to "190932"))
    }

}