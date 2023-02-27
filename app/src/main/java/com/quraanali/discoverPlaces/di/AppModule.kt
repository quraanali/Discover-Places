package com.quraanali.discoverPlaces.di

import com.quraanali.discoverPlaces.ui.base.binding.BindingAdapters
import com.quraanali.discoverPlaces.ui.base.binding.BindingAdaptersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideBindingAdapters(): BindingAdapters {
        return BindingAdaptersImpl()
    }
}