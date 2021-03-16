package io.techmeskills.an02onl_plannerapp.support

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

interface OnSystemBarsSizeChangedListener {
    val insets: VerticalInset
    fun insetsChanged(statusBarSize: Int, navigationBarSize: Int, hasKeyboard: Boolean)
}

data class VerticalInset(val top: Int, val bottom: Int, val hasKeyboard: Boolean) {
    companion object {
        fun empty() = VerticalInset(0, 0, false)
    }
}

fun Dialog.setWindowTransparency(
    listener: OnSystemBarsSizeChangedListener
) {
    window?.decorView?.overrideSystemInsets(listener)
    window?.navigationBarColor = Color.TRANSPARENT
    window?.statusBarColor = Color.TRANSPARENT
}

fun Activity.setWindowTransparency(
    listener: OnSystemBarsSizeChangedListener
) {
    window.decorView.overrideSystemInsets(listener)
    window.navigationBarColor = Color.TRANSPARENT
    window.statusBarColor = Color.TRANSPARENT
}

fun View.overrideSystemInsets(listener: OnSystemBarsSizeChangedListener) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->

        val desiredBottomInset = calculateDesiredBottomInset(
            this,
            insets.systemWindowInsetTop,
            insets.systemWindowInsetBottom,
            listener
        )

        ViewCompat.onApplyWindowInsets(
            this,
            WindowInsetsCompat.Builder(insets)
                .setSystemWindowInsets(Insets.of(0, 0, 0, desiredBottomInset))
                .build()
        )
    }
}

fun calculateDesiredBottomInset(
    view: View,
    topInset: Int,
    bottomInset: Int,
    listener: OnSystemBarsSizeChangedListener
): Int {
    val hasKeyboard = view.isKeyboardAppeared(bottomInset)
    val desiredBottomInset = if (hasKeyboard) bottomInset else 0
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
        listener.insetsChanged(topInset, bottomInset, hasKeyboard)
    } else {
        listener.insetsChanged(topInset, if (hasKeyboard) 0 else bottomInset, hasKeyboard)
    }
    return desiredBottomInset
}

private fun View.isKeyboardAppeared(bottomInset: Int) =
    bottomInset / resources.displayMetrics.heightPixels.toDouble() > .25
