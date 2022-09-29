package com.candem.guessthenumber.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GameArg(
    val number: Set<Int>
): Parcelable