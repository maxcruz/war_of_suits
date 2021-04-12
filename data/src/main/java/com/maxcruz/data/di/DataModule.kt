package com.maxcruz.data.di

import com.maxcruz.data.InMemoryDataSource
import com.maxcruz.data.datasource.GameDataSource
import com.maxcruz.data.datasource.PreferencesDataSource
import com.maxcruz.data.datasource.SessionDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindPreferenceDataSource(
        inMemoryDataSource: InMemoryDataSource
    ): PreferencesDataSource

    @Binds
    abstract fun bindSessionDataSource(
        inMemoryDataSource: InMemoryDataSource
    ): SessionDataSource

    @Binds
    abstract fun bindGameDataSource(
        inMemoryDataSource: InMemoryDataSource
    ): GameDataSource
}