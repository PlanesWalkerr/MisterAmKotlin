package com.makhovyk.misteramkotlin.Utils

import android.content.ContentResolver
import android.provider.Settings
import com.makhovyk.misteramkotlin.R

class Utils {
    companion object {
        fun getDeviceId(contentResolver: ContentResolver): String {
            return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        }

        fun getTagDrawableResource(tag: String): Int {
            var drawable: Int = 0
            when (tag) {
                "gift" -> drawable = R.drawable.ic_gift
                "drinks" -> drawable = R.drawable.ic_drinks
                "soup" -> drawable = R.drawable.ic_soup
                "delivery to door" -> drawable = R.drawable.ic_delivery_to_door
                "specific time" -> drawable = R.drawable.ic_specific_time
            }
            return drawable
        }
    }

}