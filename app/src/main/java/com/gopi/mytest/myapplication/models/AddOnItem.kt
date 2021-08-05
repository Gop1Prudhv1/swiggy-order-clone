package com.gopi.mytest.myapplication.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val VEG = "veg"

@Parcelize
data class AddOnItem (
    val type: String = VEG,
    val name: String = "",
    val price: Int = 0
): Parcelable {
}