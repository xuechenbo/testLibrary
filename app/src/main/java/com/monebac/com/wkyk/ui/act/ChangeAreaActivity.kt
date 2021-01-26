package com.monebac.com.wkyk.ui.act

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper
import com.monebac.com.R
import com.monebac.com.base.BaseVmActivity
import com.monebac.com.utils.getMap
import com.monebac.com.wkyk.adapter.ChangeAreaAdapter
import com.monebac.com.wkyk.model.AreaModel
import com.monebac.com.wkyk.ui.vm.ChangeAreaViewModel
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.act_change_area.*
import kotlinx.android.synthetic.main.dialog_calendar.tv_title
import kotlinx.android.synthetic.main.layout_title.*
import java.io.Serializable

class ChangeAreaActivity : BaseVmActivity<ChangeAreaViewModel>() {
    lateinit var mChangeAreaAdapter: ChangeAreaAdapter
    lateinit var selectAreaMap: MutableMap<String, AreaModel>
    private lateinit var mList: List<AreaModel>
    var seleType = true

    override fun layoutRes(): Int = R.layout.act_change_area

    override fun viewModelClass(): Class<ChangeAreaViewModel> = ChangeAreaViewModel::class.java

    override fun initView() {
        tv_title.text = "选择商户"
        mList = ArrayList()
        selectAreaMap = HashMap()
    }

    override fun initData() {
        initListener()
        getArea()
    }

    private fun initListener() {
        back.setOnClickListener { finish() }
        smarFresh.run {
            isEnableLoadmore = false
            isEnableRefresh = false
            refreshHeader = ClassicsHeader(context)
            setOnRefreshListener {

            }
        }
        recyCler.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        mChangeAreaAdapter = ChangeAreaAdapter(R.layout.item_change_area, mList).also {
            it.setOnItemClickListener { adapter, _, position ->
                val areaModel = adapter.data[position] as AreaModel
                if (seleType) {
                    selectAreaMap["pri"] = areaModel
                    getArea(areaModel.id)
                } else {
                    selectAreaMap["city"] = areaModel
                    getIndustry(selectAreaMap["pri"]!!.id, selectAreaMap["city"]!!.id)
                }
            }
            it.bindToRecyclerView(recyCler)
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            cityob.observe(this@ChangeAreaActivity, Observer {
                mChangeAreaAdapter.setNewData(it)
            })
            industryob.observe(this@ChangeAreaActivity, Observer {
                val intent = Intent()
                intent.putExtra("selectAreaMap", selectAreaMap as Serializable)
                intent.putExtra("Industry", it)
                setResult(1009, intent)
                finish()
            })
            isType.observe(this@ChangeAreaActivity, Observer {
                seleType = it
            })
        }
    }

    //TODO 商户
    private fun getIndustry(priId: String, cityId: String) {
        mViewModel.getIndustry(getMap(mutableMapOf("3" to "490006"
                , "30" to intent.getStringExtra("bindId")
                , "31" to priId
                , "32" to cityId
                , "33" to intent.getStringExtra("acqCode")
        )))
    }

    //TODO 省 市
    private fun getArea(id: String = "0") {
        mViewModel.getCity(getMap(mutableMapOf("3" to "490004", "41" to id)))
    }
}