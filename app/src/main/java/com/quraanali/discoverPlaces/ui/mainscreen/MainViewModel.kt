package com.quraanali.discoverPlaces.ui.mainscreen

import android.app.Application
import com.quraanali.discoverPlaces.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,

    ) : BaseViewModel(application) {

}