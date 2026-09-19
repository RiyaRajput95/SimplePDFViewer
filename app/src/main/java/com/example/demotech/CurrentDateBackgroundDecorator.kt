package com.example.demotech

import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade


class CurrentDateBackgroundDecorator(
    private val currentDay: CalendarDay,
    private val backgroundDrawable: Drawable
) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == currentDay
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(backgroundDrawable)
    }


}