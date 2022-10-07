package com.candem.guessthenumber.ui.scoreboard

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScoreViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
): ViewModel() {

    fun saveScore(key: String, value: Int) {
        viewModelScope.launch {
            val dataStoreKey = preferencesKey<Int>(key)
            dataStore.edit { scorePref ->
                scorePref[dataStoreKey] = value

            }
        }
    }

    /*fun read(key: String):Int? {
        viewModelScope.launch {
            val dataStoreKey = preferencesKey<Int>(key)
            val preferences = dataStore.data.first()
            return preferences[dataStoreKey]
        }
    }*/

}