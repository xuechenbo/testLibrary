package com.monebac.com.wkyk.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MultiMonthView


class MakePlanCalendarMonthView(context: Context) : MultiMonthView(context) {

    private var mRadius: Int = 0

    override fun onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2
        mSchemePaint.style = Paint.Style.STROKE
    }


    /**
     * 绘制选中的日子
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return 返回true 则绘制onDrawScheme，因为这里背景色不是是互斥的，所以返回true
     */
    override fun onDrawSelected(canvas: Canvas, calendar: Calendar, x: Int, y: Int, hasScheme: Boolean,
                                isSelectedPre: Boolean, isSelectedNext: Boolean): Boolean {

        //TODO 这里绘制选中的日子样式，看需求需不需要继续调用onDrawScheme

        val cx = x + mItemWidth / 2
        val cy = y + mItemHeight / 2
        if (isSelectedPre) {
            if (isSelectedNext) {
                canvas.drawRect(x.toFloat(), (cy - mRadius).toFloat(), (x + mItemWidth).toFloat(), (cy + mRadius).toFloat(), mSelectedPaint)
            } else {//最后一个，the last
                canvas.drawRect(x.toFloat(), (cy - mRadius).toFloat(), cx.toFloat(), (cy + mRadius).toFloat(), mSelectedPaint)
                canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius.toFloat(), mSelectedPaint)
            }
        } else {
            if (isSelectedNext) {
                canvas.drawRect(cx.toFloat(), (cy - mRadius).toFloat(), (x + mItemWidth).toFloat(), (cy + mRadius).toFloat(), mSelectedPaint)
            }
            canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius.toFloat(), mSelectedPaint)
            //
        }

        return false
    }


    /**
     * 绘制标记的事件日子
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int, y: Int, isSelected: Boolean) {
        //TODO 这里绘制标记的日期样式，想怎么操作就怎么操作
        //mItemWidth  每一项的宽度
        val cx = x + mItemWidth / 2
        val cy = y + mItemHeight / 2
        mSchemePaint.color = Color.RED
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius.toFloat(), mSchemePaint)
    }

    /**
     * 绘制文本
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    //TODO //这里绘制文本
    //TODO 要怎么显示你就在这里怎么画，你不画就不显示，是看你想怎么显示日历的，而不是看框架
    override fun onDrawText(canvas: Canvas, calendar: Calendar, x: Int, y: Int, hasScheme: Boolean, isSelected: Boolean) {


        val baselineY = mTextBaseLine + y
        val cx = x + mItemWidth / 2

        val isInRange = isInRange(calendar)
        val isEnable = !onCalendarIntercept(calendar)

        when {
            isSelected -> canvas.drawText(calendar.day.toString(),
                    cx.toFloat(),
                    baselineY,
                    mSelectTextPaint)
            hasScheme -> canvas.drawText(calendar.day.toString(),
                    cx.toFloat(),
                    baselineY,
                    if (calendar.isCurrentDay)
                        mCurDayTextPaint
                    else if (calendar.isCurrentMonth && isInRange && isEnable) mSchemeTextPaint else mOtherMonthTextPaint)
            else -> canvas.drawText(calendar.day.toString(), cx.toFloat(), baselineY,
                    if (calendar.isCurrentDay)
                        mCurDayTextPaint
                    else if (calendar.isCurrentMonth && isInRange && isEnable) mCurMonthTextPaint else mOtherMonthTextPaint)
        }
    }


}