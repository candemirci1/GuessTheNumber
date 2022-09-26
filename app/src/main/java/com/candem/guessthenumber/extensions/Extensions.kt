package com.candem.guessthenumber.extensions

import java.util.*

fun IntRange.random() =
    Random().nextInt((endInclusive + 1) - start) + start