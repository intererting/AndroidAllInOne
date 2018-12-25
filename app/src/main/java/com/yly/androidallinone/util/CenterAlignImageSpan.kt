package com.yly.androidallinone.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan


class CenterAlignImageSpan(drawable: Drawable, private val spaceWith: Int = 0) : ImageSpan(drawable) {
    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val d = drawable
        val rect = d.bounds

        if (fm != null) {
            fm.ascent = -rect.bottom
            fm.descent = 0

            fm.top = fm.ascent
            fm.bottom = 0
        }

        return rect.right + spaceWith
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val b = drawable
        val fm = paint.fontMetricsInt

        val transY = (y + fm.descent + y + fm.ascent) / 2f - b.bounds.bottom / 2f//计算y方向的位移
        canvas.save()
        canvas.translate(x + spaceWith / 2, transY)//绘制图片位移一段距离
        b.draw(canvas)
        canvas.restore()
    }
}