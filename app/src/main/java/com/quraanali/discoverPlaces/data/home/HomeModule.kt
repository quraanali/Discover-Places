package com.quraanali.discoverPlaces.data.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.quraanali.discoverPlaces.data.source.remote.ApiEndPoints
import com.quraanali.discoverPlaces.di.IoDispatcher
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Singleton
    @Provides
    fun provideUserDataSource(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        homeLocalDataSource: HomeLocalDataSource,
        homeRemoteDataSource: HomeRemoteDataSource,
    ): HomeDataSource {
        return HomeRepository(
            dispatcher,
            homeLocalDataSource,
            homeRemoteDataSource
        )
    }

    @Provides
    fun provideUserLocalDataSource(dataStore: DataStore<Preferences>): HomeLocalDataSource {
        return HomeLocalDataSourceImpl(dataStore)
    }

    @Provides
    fun provideUserRemoteDataSource(
        moshi: Moshi,
        apiEndPoints: ApiEndPoints
    ): HomeRemoteDataSource {
        return HomeRemoteDataSourceImpl(apiEndPoints)
    }
}
