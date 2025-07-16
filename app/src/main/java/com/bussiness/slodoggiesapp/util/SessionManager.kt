package com.bussiness.slodoggiesapp.util

import android.content.Context
import android.content.SharedPreferences
import com.bussiness.slodoggiesapp.model.UserType
import androidx.core.content.edit
import com.bussiness.slodoggiesapp.util.AppConstant.KEY_IS_LOGGED_IN
import com.bussiness.slodoggiesapp.util.AppConstant.KEY_IS_SKIP_LOGIN
import com.bussiness.slodoggiesapp.util.AppConstant.KEY_USER_TYPE

class SessionManager(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)


    // Save user login state
    fun setLogin(value: Boolean) {
        preferences.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, value)
            if (value) {
                putBoolean(KEY_IS_SKIP_LOGIN, false)
            }
            apply()
        }
    }

    // Save skip login state
    fun setSkipLogin(value: Boolean) {
        preferences.edit().apply {
            putBoolean(KEY_IS_SKIP_LOGIN, value)
            if (value) {
                putBoolean(KEY_IS_LOGGED_IN, false)
            }
            apply()
        }
    }

    //  Save user type (OWNER or PROFESSIONAL)
    fun setUserType(userType: UserType) {
        preferences.edit { putString(KEY_USER_TYPE, userType.name) }
    }

    //  Get saved user type
    fun getUserType(): UserType? {
        val type = preferences.getString(KEY_USER_TYPE, null)
        return type?.let { UserType.valueOf(it) }
    }

    //  clear session on logout
    fun clearSession() {
        preferences.edit { clear() }
    }
}
