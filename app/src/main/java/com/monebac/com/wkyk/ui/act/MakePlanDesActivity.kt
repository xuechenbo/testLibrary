package com.monebac.com.wkyk.ui.act

import android.content.Intent
import androidx.lifecycle.Observer
import com.monebac.com.R
import com.monebac.com.base.BaseVmActivity
import com.monebac.com.utils.getMap
import com.monebac.com.wkyk.dialogfrg.CalendarDialogFrg
import com.monebac.com.wkyk.dialogfrg.ChangeTypeDialog
import com.monebac.com.wkyk.dialogfrg.ShowPlanMsgDialog
import com.monebac.com.wkyk.event.ClosePlanEvent
import com.monebac.com.wkyk.model.AreaModel
import com.monebac.com.wkyk.model.BindCard
import com.monebac.com.wkyk.ui.vm.MakePlanViewModel
import kotlinx.android.synthetic.main.activity_make_design.*
import kotlinx.android.synthetic.main.layout_plan_header.*
import kotlinx.android.synthetic.main.layout_title.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class MakePlanDesActivity : BaseVmActivity<MakePlanViewModel>() {
    lateinit var bindCard: BindCard
    private var acqCode: String = ""
    var dailyNum: String = "2"
    private lateinit var Industry: String
    lateinit var selectAreaMap: MutableMap<String, AreaModel>

    override fun viewModelClass(): Class<MakePlanViewModel> = MakePlanViewModel::class.java

    override fun layoutRes(): Int = R.layout.activity_make_design

    override fun initView() {
        EventBus.getDefault().register(this)
        bindCard = intent.getSerializableExtra("BindCard_Class") as BindCard
        acqCode = intent.getStringExtra("acqCode")
        tv_title.text = "制定计划"
    }

    override fun initData() {
        tv_bank_name.text = bindCard.BANK_NAME
        tv_bank_account.text = "尾号:${bindCard.BANK_ACCOUNT.run {
            substring(length - 4, length)
        }}"
        tv_billDay.text = bindCard.BILL_DAY
        tv_payDay.text = bindCard.REPAYMENT_DAY
        tv_limit.text = bindCard.LIMIT_MONEY
        initListener()
        //
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            plamMsg.observe(this@MakePlanDesActivity, Observer {
                it.run {
                    address = selectAreaMap
                    industryStr = Industry
                    AcqCode = acqCode
                    acqName = intent.getStringExtra("acqName")
                    rNum = dailyNum
                }
                val instance = ShowPlanMsgDialog().getInstance(it)
                instance.success {
                    startActivity<PreviewDetailActivity>("previewPlanModel" to it,
                            "bindCard" to bindCard)
                }
                instance.show(supportFragmentManager, "")
            })
            mLoading.observe(this@MakePlanDesActivity, Observer {
                if (it) showProgressDialog(R.string.loading) else dismissProgressDialog()
            })
        }
    }

    private fun initListener() {
        back.setOnClickListener { finish() }
        tv_totalTime.setOnClickListener {
            val calendarDialogFrg = CalendarDialogFrg()
            calendarDialogFrg.setSuccess {
                tv_totalTime.text = it.toString()
            }
            calendarDialogFrg.show(supportFragmentManager, "")
        }

        //每日几笔
        tv_hkbs.setOnClickListener {
            ChangeTypeDialog().getInstance(listOf("每天2笔", "每天3笔"), "").also { it ->
                it.setSuccess {
                    dailyNum = it
                    tv_hkbs.text = "每日${it}笔"
                }
                it.setOnClickListener { s, s2 ->
                    tv_hkbs.text = "$s$s2"
                }
                it.show(supportFragmentManager, "")
            }
        }

        //地区
        tv_choiceArea.setOnClickListener {
            startActivityForResult<ChangeAreaActivity>(0,
                    "bindId" to bindCard.ID,
                    "acqCode" to acqCode)
        }

        bt_calculate.setOnClickListener {
            when {
                et_inputPayAmount.text.isNullOrEmpty() -> toast("还款金额为空")
                et_principalMoney.text.isNullOrEmpty() -> toast("可用额度为空")
                tv_choiceArea.text.isNullOrEmpty() -> toast("请选择地区")
                else -> mViewModel.getPlanMsg(requestData())
            }
        }


    }

    private fun requestData(): MutableMap<String, String> =
            getMap(mutableMapOf(
                    "3" to "193000",
                    "7" to dailyNum,
                    "8" to et_inputPayAmount.text.toString(),
                    "9" to "1",
                    "10" to "0",
                    "11" to bindCard.BANK_ACCOUNT,
                    "12" to bindCard.ID,
                    "16" to "0",
                    "35" to selectAreaMap["city"]!!.id,
                    "36" to selectAreaMap["pri"]!!.id,
                    "43" to "1",
                    "43" to "$acqCode",
                    "44" to et_principalMoney.text.toString())
            )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == 1009) {
            data?.run {
                selectAreaMap = data!!.extras.getSerializable("selectAreaMap") as MutableMap<String, AreaModel>
                Industry = extras.getString("Industry")
                tv_choiceArea.text = "${selectAreaMap["pri"]!!.divisionName}-${selectAreaMap["city"]!!.divisionName}"
            }
        }
    }

    @Subscribe
    fun onEvent(closePlanEvent: ClosePlanEvent) {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}