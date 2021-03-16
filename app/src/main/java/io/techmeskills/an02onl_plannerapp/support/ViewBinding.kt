package io.techmeskills.an02onl_plannerapp.support

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KCallable

interface ViewBindable<T : ViewBinding> {
    val viewBinding: T
}

inline fun <reified T : ViewBinding> ViewGroup.viewBinding(method: KCallable<*>): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) {
        method.call(LayoutInflater.from(context), this) as T
    }

abstract class BindingViewHolder<T : ViewBinding>(override val viewBinding: T) :
    RecyclerView.ViewHolder(viewBinding.root), ViewBindable<T>
