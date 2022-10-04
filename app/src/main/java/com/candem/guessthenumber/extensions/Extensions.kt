package com.candem.guessthenumber.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun IntRange.random() =
    Random().nextInt((endInclusive + 1) - start) + start


fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun onSNACK(view: View){
    //Snackbar(view)
    val snackbar = Snackbar.make(view, "TRY AGAİN",
        Snackbar.LENGTH_LONG).setAction("Action", null)
    snackbar.setActionTextColor(Color.BLUE)
    val snackbarView = snackbar.view
    snackbarView.setBackgroundColor(Color.LTGRAY)
    val textView =
        snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(Color.BLUE)
    textView.textSize = 28f
    snackbar.show()
}