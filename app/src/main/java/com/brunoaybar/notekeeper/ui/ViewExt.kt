package com.brunoaybar.notekeeper.ui

import android.app.Activity
import android.content.res.Resources
import android.view.View
import android.support.v7.view.menu.ActionMenuItemView
import android.widget.ImageButton
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v7.widget.ActionMenuView
import android.support.v7.widget.Toolbar


fun isRtl(resources: Resources): Boolean {
    return resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
}

fun Activity.getThemeColor(id: Int): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(id))
    val result = a.getColor(0, 0)
    a.recycle()
    return result
}

fun Toolbar.colorize(toolbarIconsColor: Int){
    val colorFilter = PorterDuffColorFilter(toolbarIconsColor, PorterDuff.Mode.MULTIPLY)

    for (i in 0 until childCount) {
        val v = getChildAt(i)

        //Step 1 : Changing the color of back button (or open drawer button).
        if (v is ImageButton) {
            //Action Bar back button
            v.drawable.colorFilter = colorFilter
        }

        if (v is ActionMenuView) {
            for (j in 0 until v.getChildCount()) {

                //Step 2: Changing the color of any ActionMenuViews - icons that
                //are not back button, nor text, nor overflow menu icon.
                val innerView = v.getChildAt(j)

                if (innerView is ActionMenuItemView) {
                    val drawablesCount = innerView.compoundDrawables.size
                    for (k in 0 until drawablesCount) {
                        if (innerView.compoundDrawables[k] != null) {

                            //Important to set the color filter in seperate thread,
                            //by adding it to the message queue
                            //Won't work otherwise.
                            innerView.post({ innerView.compoundDrawables[k].colorFilter = colorFilter })
                        }
                    }
                }
            }
        }

        //Step 3: Changing the color of title and subtitle.
        setTitleTextColor(toolbarIconsColor)
        setSubtitleTextColor(toolbarIconsColor)

    }
}