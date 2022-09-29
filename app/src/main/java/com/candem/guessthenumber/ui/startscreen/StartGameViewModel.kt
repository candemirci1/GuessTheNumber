package com.candem.guessthenumber.ui.startscreen

import androidx.lifecycle.ViewModel
import com.candem.guessthenumber.extensions.random

class StartGameViewModel: ViewModel() {

    fun createRandomNumber(): MutableSet<Int> {
        val numberSet: MutableSet<Int> = mutableSetOf()
        while (numberSet.size < 4) {
            val digit = (0..9).random()
            if (!(digit == 0 && numberSet.isEmpty())) {
                numberSet.add(digit)
            }
        }
        return numberSet
    }
}