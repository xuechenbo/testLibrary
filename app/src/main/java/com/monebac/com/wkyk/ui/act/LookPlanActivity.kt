package com.monebac.com.wkyk.ui.act

import androidx.lifecycle.Observer
import com.monebac.com.R
import com.monebac.com.base.BaseVmActivity
import com.monebac.com.utils.getMap
import com.monebac.com.wkyk.adapter.LookPlanAdapter
import com.monebac.com.wkyk.model.BindCard
import com.monebac.com.wkyk.model.PlanAllModel
import com.monebac.com.wkyk.ui.vm.LookPlanViewModel
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_bank_list.*
import kotlinx.android.synthetic.main.layout_title.*
import org.jetbrains.anko.startActivity

class LookPlanActivity : BaseVmActivity<LookPlanViewModel>() {
    lateinit var bindCard: BindCard
    private lateinit var mList: List<PlanAllModel>
    private lateinit var lookPlanAdapter: LookPlanAdapter

    override fun layoutRes(): Int = R.layout.activity_look_plan

    override fun viewModelClass(): Class<LookPlanViewModel> = LookPlanViewModel::class.java

    override fun initView() {
        tv_title.text = "查看计划"
        bindCard = intent.getSerializableExtra("BindCard_Class") as BindCard
        mList = ArrayList()
    }

    override fun initData() {
        initListener()
        smarFresh.autoRefresh()
    }

    val initListener = {
        back.setOnClickListener { finish() }
        lookPlanAdapter = LookPlanAdapter(mList).also {
            it.setOnItemChildClickListener { Ad, view, position ->
                val planAllModel = Ad.data[position] as PlanAllModel
                when (view.id) {
                    R.id.btn_detail -> startActivity<PlanDetailActivity>(
                            "planDetail" to planAllModel,
                            "bindCard" to bindCard)
                }
            }
            recyCler.adapter = it
        }

        smarFresh.run {
            refreshHeader = ClassicsHeader(context)
            isEnableLoadmore = false
            setOnRefreshListener {
                mViewModel.getListData(
                        getMap(mutableMapOf(
                                "3" to "190212",
                                "43" to bindCard.BANK_ACCOUNT))
                )
            }
        }


    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            lookup.observe(this@LookPlanActivity, Observer {
                smarFresh.finishRefresh()
                lookPlanAdapter.setNewData(it)
            })

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}