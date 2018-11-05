package com.makhovyk.misteramkotlin.Utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    val PREF_NAME = "UserSession"
    val APP_TOKEN = "appToken"
    val AUTH_TOKEN = "authToken"
    val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var authToken: String?
        get() = sharedPreferences.getString(AUTH_TOKEN, null)
        set(value) = sharedPreferences.edit {
            it.putString(AUTH_TOKEN, value)
        }

    var appToken: String?
        get() = sharedPreferences.getString(APP_TOKEN, null)
        set(value) = sharedPreferences.edit {
            it.putString(APP_TOKEN, value)
        }

    public fun isLoggedIn() : Boolean {
        return authToken!=null
    }

    public fun isAppRegistered() : Boolean {
        return appToken!=null
    }

    public fun logOut() {
        sharedPreferences.edit {
            it.remove(AUTH_TOKEN)
        }
    }
}