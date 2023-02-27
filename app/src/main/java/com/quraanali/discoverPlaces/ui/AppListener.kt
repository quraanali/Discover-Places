package com.quraanali.discoverPlaces.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar

interface AppListener {
    fun showProgress()
    fun hideProgress()

    fun initNavDrawer(toolbar: Toolbar?)

    fun initPushNotification()

    fun changeLocal(bundle: Bundle?=null)
}