package com.candem.guessthenumber.extensions

import android.content.Context
import android.widget.Toast
import java.util.*

fun IntRange.random() =
    Random().nextInt((endInclusive + 1) - start) + start


fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}