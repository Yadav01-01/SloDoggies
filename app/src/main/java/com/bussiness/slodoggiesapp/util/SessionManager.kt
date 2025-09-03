package com.bussiness.slodoggiesapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.util.AppConstant.KEY_IS_LOGGED_IN
import com.bussiness.slodoggiesapp.util.AppConstant.KEY_IS_SKIP_LOGIN
import com.bussiness.slodoggiesapp.util.AppConstant.KEY_USER_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * SessionManager - Handles user session data with SharedPreferences.
 * Safe for production usage with null handling and default values.
 */
class SessionManager @Inject constructor(@ApplicationContext context: Context) {

    private val preferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "app_preferences"

        @Volatile
        private var instance: SessionManager? = null

        fun getInstance(context: Context): SessionManager {
            return instance ?: synchronized(this) {
                instance ?: SessionManager(context).also { instance = it }
            }
        }
    }

    /** Save user login state */
    fun setLogin(value: Boolean) {
        preferences.edit {
            putBoolean(KEY_IS_LOGGED_IN, value)
            if (value) putBoolean(KEY_IS_SKIP_LOGIN, false)
        }
    }

    /** Get user login state */
    fun isLoggedIn(): Boolean = preferences.getBoolean(KEY_IS_LOGGED_IN, false)

    /** Save user type (OWNER or PROFESSIONAL) */
    fun setUserType(userType: UserType) {
        preferences.edit { putString(KEY_USER_TYPE, userType.name) }
    }

    /** Get saved user type safely */
    fun getUserType(): UserType {
        val type = preferences.getString(KEY_USER_TYPE, UserType.PET_OWNER.name)
        return try {
            UserType.valueOf(type!!)
        } catch (e: Exception) {
            UserType.PET_OWNER // default fallback
        }
    }

    /** Clear session on logout */
    fun clearSession() {
        preferences.edit { clear() }
    }
}
