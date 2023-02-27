package com.quraanali.discoverPlaces.ui.base

import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.fragment.app.Fragment
import com.quraanali.discoverPlaces.ui.AppListener
import java.util.*

open class BaseFragment : Fragment() {

    private lateinit var listener: AppListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement AppListener")
        }
    }

    protected open fun showProgress(isLoading: Boolean) {
        if (isLoading) {
            listener.showProgress()
        } else {
            listener.hideProgress()
        }
    }



    fun changeLocal() {
        listener.changeLocal()
    }


    fun getCurrentLanguage(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getDefault()[0].language
        } else {
            Locale.getDefault().language
        }
    }


}