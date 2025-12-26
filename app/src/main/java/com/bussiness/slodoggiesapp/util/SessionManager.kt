package com.bussiness.slodoggiesapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.bussiness.slodoggiesapp.data.model.main.UserType
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

        // Keys for new data
        private const val KEY_USER_ID = "key_user_id"
        private const val KEY_USER_NAME = "key_user_name"
        private const val KEY_USER_EMAIL = "key_user_email"
        private const val KEY_USER_IMAGE = "key_user_image"
        private const val KEY_TOKEN = "key_token"
        private const val KEY_SIGNUP_FLOW = "signup_flow"

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

    /** Save user type (Owner or Professional) */
    fun setUserType(userType: UserType) {
        preferences.edit { putString(KEY_USER_TYPE, userType.name) }
    }

    /** Get saved user type safely */
    fun getUserType(): UserType {
        val type = preferences.getString(KEY_USER_TYPE, UserType.Owner.name)
        return try {
            UserType.valueOf(type ?: UserType.Owner.name)
        } catch (e: Exception) {
            UserType.Owner // default fallback
        }
    }

    /** Save user ID */
    fun setUserId(userId: String) {
        preferences.edit { putString(KEY_USER_ID, userId) }
    }

    /** Save user Name */
    fun setUserName(name: String) {
        preferences.edit { putString(KEY_USER_NAME, name) }
    }

    fun setUserEmail(email: String) {
        preferences.edit { putString(KEY_USER_EMAIL, email) }
    }

    fun getUserEmail() : String {
        return preferences.getString(KEY_USER_EMAIL, "") ?: ""
    }

    /** Save user Image */
    fun setUserImage(image: String) {
        preferences.edit { putString(KEY_USER_IMAGE, image) }
    }

    /** Get user ID safely */
    fun getUserId(): String {
        return preferences.getString(KEY_USER_ID, "") ?: ""
    }

    /** Get user Name safely */
    fun getUserName(): String {
        return preferences.getString(KEY_USER_NAME, "") ?: ""
    }

    /** Get user Name safely */
    fun getUserImage(): String {
        return preferences.getString(KEY_USER_IMAGE, "") ?: ""
    }

    /** Save auth token */
    fun setToken(token: String) {
        preferences.edit { putString(KEY_TOKEN, token) }
    }

    /** Get saved auth token safely */
    fun getToken(): String {
        return preferences.getString(KEY_TOKEN, "") ?: ""
    }

    fun setSignupFlow(active: Boolean) {
        preferences.edit { putBoolean(KEY_SIGNUP_FLOW, active) }
    }

    fun isSignupFlowActive(): Boolean {
        return preferences.getBoolean(KEY_SIGNUP_FLOW, false)
    }

    fun clearSignupFlow() {
        preferences.edit { remove(KEY_SIGNUP_FLOW) }
    }

    /** Clear session on logout */
    fun clearSession() {
        preferences.edit { clear() }
    }
}
