package com.monebac.com.wkyk.dialogfrg

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.monebac.com.R
import com.monebac.com.utils.LogsUtils
import com.monebac.com.utils.toYMD
import kotlinx.android.synthetic.main.dialog_calendar.view.*
import org.jetbrains.anko.support.v4.toast

class CalendarDialogFrg : DialogFragment() {

    private var mSuccessCallback: SuccessCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.MyDialog)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        dialog!!.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        if (dialog != null) {
            val dm = DisplayMetrics()
            activity!!.windowManager.defaultDisplay.getMetrics(dm)
            dialog.window!!.setLayout((dm.widthPixels * 1), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.dialog_calendar, container)
        initData()
        initListener(inflate)
        return inflate
    }


    private fun initData() {
        dialog!!.setCanceledOnTouchOutside(false)
        val window = dialog!!.window
        window!!.setGravity(Gravity.BOTTOM)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @SuppressLint("SetTextI18n")
    private fun initListener(inflate: View) {
        inflate.run {

            tv_month_day.text = "${calendarView.curMonth}月${calendarView.curDay}日"

            tv_close.setOnClickListener { dialog!!.dismiss() }
            tv_save.setOnClickListener {
                var mList = calendarView.multiSelectCalendars
                //TODO 1
//                if (mSuccessCallback != null) {
//                    mSuccessCallback!!.success(mList)
//                }

                val calendar = mList[0]
                calendar.timeInMillis

                //TODO 2
                mListener?.invoke(mList.map {
                    it.timeInMillis.toYMD()
                })

                dialog!!.dismiss()
            }

            calendarView.run {
                //动态设置日历的选择日期范围
                setRange(curYear, curMonth, curDay, 2030, 12, 30)

                setOnCalendarMultiSelectListener(object : CalendarView.OnCalendarMultiSelectListener {
                    override fun onMultiSelectOutOfSize(calendar: Calendar?, maxSize: Int) {
                        toast("超过最大选择数量 ：$maxSize")
                    }

                    override fun onCalendarMultiSelectOutOfRange(calendar: Calendar?) {

                    }

                    override fun onCalendarMultiSelect(calendar: Calendar?, curSize: Int, maxSize: Int) {
                        //点击事件
                        inflate.tv_month_day.text = "${calendar!!.month}月${calendar.day}日"
                        LogsUtils.e("${calendar.day}onCalendarMultiSelect")
                    }
                })

                //单选
                setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener {
                    override fun onCalendarOutOfRange(calendar: Calendar) {

                    }

                    override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {

                    }
                })

                //拦截器
                setOnCalendarInterceptListener(object : CalendarView.OnCalendarInterceptListener {
                    override fun onCalendarInterceptClick(calendar: Calendar?, isClick: Boolean) {
                        toast("不在指定日期中")
                    }

                    override fun onCalendarIntercept(calendar: Calendar): Boolean {
                        return calendar.day < 5 || calendar.day > 20
                    }
                })
                //TODO 月模式滑动监听
                setOnMonthChangeListener { year, month ->
                    LogsUtils.e(" -- $year  --  $month")
                    var calendar = selectedCalendar
                    inflate.tv_month_day.text = "${year}年${month}月"
                }


            }
        }
    }


    //TODO java转换
    interface SuccessCallback {
        fun success(value: MutableList<Calendar>)
    }

    fun setSuccessCallback(successCallback: SuccessCallback) {
        mSuccessCallback = successCallback
    }


    //TODO  方法二
    lateinit var mListener: (List<String>) -> Unit

    fun setSuccess(mlistener: (List<String>) -> Unit) {
        mListener = mlistener

    }


}

