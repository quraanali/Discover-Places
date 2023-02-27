package com.quraanali.discoverPlaces.ui.base

import android.text.BidiFormatter
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

open class BaseBottomSheet : BottomSheetDialogFragment() {


    open fun getProgressView(): View? {
        return null
    }

    protected open fun showProgress(show: Boolean) {
        if (show) {
            getProgressView()?.visibility = View.VISIBLE
        } else {
            getProgressView()?.visibility = View.GONE
        }
    }

    private val Fragment.rootView get() = view?.rootView

    fun getTextDirection(value: String): String {
        return BidiFormatter.getInstance(Locale.getDefault()).unicodeWrap(value)
    }

    open fun getViewBinding(): View? {
        return null
    }
}