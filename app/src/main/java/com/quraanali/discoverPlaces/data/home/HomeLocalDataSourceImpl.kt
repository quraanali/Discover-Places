package com.quraanali.discoverPlaces.data.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HomeLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : HomeLocalDataSource {

}
