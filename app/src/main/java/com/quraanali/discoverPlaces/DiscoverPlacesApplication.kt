package com.quraanali.discoverPlaces

import android.app.Application
import android.content.Context
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.quraanali.discoverPlaces.ui.base.binding.BindingAdaptersImpl
import com.quraanali.discoverPlaces.utils.LocalizationHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class DiscoverPlacesApplication : Application(), DataBindingComponent {

    @Inject
    lateinit var appBindingAdapters: BindingAdaptersImpl

    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(this)
    }

    override fun getBindingAdapters(): BindingAdaptersImpl {
        return appBindingAdapters
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalizationHelper.onAttach(base))
    }
}