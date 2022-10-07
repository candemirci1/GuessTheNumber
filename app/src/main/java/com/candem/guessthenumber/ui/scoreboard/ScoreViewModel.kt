package com.candem.guessthenumber.ui.scoreboard

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
): ViewModel() {

    fun saveScore(key: String, value: Int) {
        viewModelScope.launch {
            val dataStoreKey = preferencesKey<Int>(key)
            val preferences = dataStore.data.first()
            val oldScore = preferences[dataStoreKey] ?: 0
            dataStore.edit { scorePref ->
                scorePref[dataStoreKey] = value + oldScore

            }
        }
    }

    fun savePlayedGameCount(key: String) {
        viewModelScope.launch {
            val dataStoreKey = preferencesKey<Int>(key)
            val preferences = dataStore.data.first()
            val oldCount = preferences[dataStoreKey] ?: 0
            dataStore.edit { scorePref ->
                scorePref[dataStoreKey] = oldCount + 1
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