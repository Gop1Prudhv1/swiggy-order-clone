package com.gopi.mytest.myapplication.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainItem(
    val name: String = "",
    val type: String = VEG,
    val price: Int = 0,
    val addlnItems: List<AddOn>? = null
): Parcelable {
}