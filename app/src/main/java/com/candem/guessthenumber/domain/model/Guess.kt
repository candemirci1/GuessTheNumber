package com.candem.guessthenumber.domain.model

data class Guess(
    val guessedNumber: Set<Int>,
    val result: Pair<Int, Int>
)