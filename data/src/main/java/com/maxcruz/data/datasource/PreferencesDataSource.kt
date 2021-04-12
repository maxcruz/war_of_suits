package com.maxcruz.data.datasource

interface PreferencesDataSource {

    fun getUserIdentifier(): String?

    fun saveUserIdentifier(identifier: String)
}