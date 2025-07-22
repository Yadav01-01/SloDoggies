package com.bussiness.slodoggiesapp.ui.component

import android.content.Context
import android.content.Intent

fun shareApp(context: Context) {
    val appPackageName = "com.business.zyvo"
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "Buy this best app at: https://play.google.com/store/apps/details?id=$appPackageName"
        )
        type = "text/plain"
    }
    context.startActivity(sendIntent)
}