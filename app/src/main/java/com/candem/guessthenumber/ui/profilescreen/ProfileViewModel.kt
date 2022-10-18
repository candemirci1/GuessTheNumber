package com.candem.guessthenumber.ui.profilescreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.candem.guessthenumber.domain.model.Statistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
): ViewModel() {

    var state = MutableStateFlow<Statistic>(Statistic(0,0))



    fun getStatistic(keyScore: String, keyCount: String) {
        viewModelScope.launch {
            val scoreKey = preferencesKey<Int>(keyScore)
            val countKey = preferencesKey<Int>(keyCount)
            val preferences = dataStore.data.first()
            val score = preferences[scoreKey] ?: 0
            val count = preferences[countKey] ?: 0
            state.value = Statistic(score,count)
        }
    }

}