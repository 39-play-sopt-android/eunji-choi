package org.sopt.play.data.local.datasource

import android.content.Context
import androidx.core.content.edit

class AuthLocalDataSource(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUser(name: String, email: String, password: String) = prefs.edit {
        putString(PREFS_NAME_KEY, name)
        putString(PREFS_EMAIL_KEY, email)
        putString(PREFS_PASSWORD_KEY, password)
    }

    fun setLoggedInState(state: Boolean) = prefs.edit { putBoolean(PREFS_IS_LOGGED_IN_KEY, state) }

    fun getName(): String? = prefs.getString(PREFS_NAME_KEY, null)

    fun getEmail(): String? = prefs.getString(PREFS_EMAIL_KEY, null)

    fun getPassword(): String? = prefs.getString(PREFS_PASSWORD_KEY, null)

    fun getIsLoggedIn(): Boolean = prefs.getBoolean(PREFS_IS_LOGGED_IN_KEY, false)

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val PREFS_NAME_KEY = "nameKey"
        private const val PREFS_EMAIL_KEY = "emailKey"
        private const val PREFS_PASSWORD_KEY = "passwordKey"
        private const val PREFS_IS_LOGGED_IN_KEY = "isLoggedInKey"
    }
}
