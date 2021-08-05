package com.gopi.mytest.myapplication.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddOn(
    val name: String = "",
    val items: List<AddOnItem>? = null,
    val maxAllowed: Int = Int.MAX_VALUE
) : Parcelable
