package io.techmeskills.an02onl_plannerapp.support

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

abstract class NavigationFragment<T : ViewBinding>(@LayoutRes layoutResId: Int) :
    SupportFragmentInset<T>(layoutResId) {

    open val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }
}