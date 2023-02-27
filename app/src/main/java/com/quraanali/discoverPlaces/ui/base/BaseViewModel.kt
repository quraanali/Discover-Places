package com.quraanali.discoverPlaces.ui.base

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.lifecycle.ViewModel
import com.quraanali.discoverPlaces.utils.LocalizationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(private val application: Application) :
    ViewModel() {


    fun getApplication(): Context {
        return LocalizationHelper.onAttach(application)
    }

    fun getCurrentLanguage(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getDefault()[0].language
        } else {
            Locale.getDefault().language
        }
    }

}