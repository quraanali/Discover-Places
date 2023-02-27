package com.quraanali.discoverPlaces.ui.base

import android.widget.Toast
import androidx.fragment.app.DialogFragment

open class BaseDialog : DialogFragment() {

    fun showToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun showToastMessage(message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}