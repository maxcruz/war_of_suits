package com.maxcruz.core.di

import com.maxcruz.core.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
internal object ThreadingModule {

    @Provides
    fun provideDispatcherProvider() : DispatcherProvider = object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = Dispatchers.Default

        override fun io(): CoroutineDispatcher = Dispatchers.IO

        override fun main(): CoroutineDispatcher = Dispatchers.Main
    }
}
