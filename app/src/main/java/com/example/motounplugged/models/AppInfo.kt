package com.example.motounplugged.models

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppInfo(
    val name: String,
    val packageName: String,
): Parcelable