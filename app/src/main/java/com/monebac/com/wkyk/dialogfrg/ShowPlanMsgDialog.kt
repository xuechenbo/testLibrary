package com.monebac.com.wkyk.dialogfrg

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.monebac.com.R
import com.monebac.com.wkyk.model.PreviewPlanModel
import kotlinx.android.synthetic.main.dialogfragment_showplan.view.*

class ShowPlanMsgDialog : DialogFragment() {
    lateinit var previewPlanModel: PreviewPlanModel

    fun getInstance(previewPlanModel: PreviewPlanModel): ShowPlanMsgDialog {
        val showPlanMsgDialog = ShowPlanMsgDialog()
        val bundle = Bundle()
        bundle.putSerializable("model", previewPlanModel)
        showPlanMsgDialog.arguments = bundle
        return showPlanMsgDialog
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.MyDialog)
        previewPlanModel = arguments!!.getSerializable("model") as PreviewPlanModel
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = View.inflate(activity, R.layout.dialogfragment_showplan, container)
        initData(inflate)
        return inflate
    }

    fun initData(view: View) {
        dialog!!.setCanceledOnTouchOutside(false)
        val window = dialog!!.window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.run {
            tv_hkje.text = previewPlanModel.HkMoney
            tv_hkzq.text = "${previewPlanModel.startTime}至${previewPlanModel.endTime}"
            tv_hkzbs.text = previewPlanModel.totoNum
            tv_workingFund.text = previewPlanModel.ZzjMoney
            tv_sxf.text = previewPlanModel.fee
            tv_bsf.text = previewPlanModel.timeFee
            tv_totalMoney.text = previewPlanModel.totalFee
            tv_address.text = previewPlanModel.address.run {
                "${this?.get("pri")!!.divisionName}-${this?.get("city")!!.divisionName}"
            }
            close.setOnClickListener { dialog!!.dismiss() }
            tv_submit.setOnClickListener {
                myListener?.invoke()
                dialog!!.dismiss()
            }
        }
    }


    private var myListener: () -> Unit = {}

    fun success(mMsg: () -> Unit) {
        this.myListener = mMsg
    }


}