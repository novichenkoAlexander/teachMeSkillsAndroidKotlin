package io.techmeskills.an02onl_plannerapp.support

import android.view.View
import android.view.ViewGroup

fun View.setVerticalMargin(marginTop: Int = 0, marginBottom: Int = 0) {
    val _layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    _layoutParams.setMargins(
        _layoutParams.leftMargin,
        marginTop,
        _layoutParams.rightMargin,
        marginBottom
    )
    this.layoutParams = _layoutParams
}

fun View.addVerticalMargin(marginTop: Int = 0, marginBottom: Int = 0) {
    val _layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    _layoutParams.setMargins(
        0,
        marginTop + _layoutParams.topMargin,
        0,
        marginBottom + _layoutParams.bottomMargin
    )
    this.layoutParams = _layoutParams
}