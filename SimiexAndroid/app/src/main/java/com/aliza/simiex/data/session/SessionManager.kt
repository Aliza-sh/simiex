package com.aliza.simiex.data.session

import android.util.Log
import com.aliza.simiex.utils.extensions.isInternetAvailable
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionManager {

    suspend fun getCurrentUser(): ParseUser? = withContext(Dispatchers.IO) {
        try {
            ParseUser.getCurrentUser()?.fetch()
        } catch (e: ParseException) {
            Log.e("SessionToken", "getCurrentUser: ${e.message}")
            null
        }
    }

    suspend fun getSessionToken(): String? = withContext(Dispatchers.IO) {
        try {
            ParseUser.getCurrentSessionToken()
        } catch (e: ParseException) {
            Log.e("SessionToken", "getSessionToken: ${e.message}")
            null
        }
    }

    suspend fun isSessionValid(): Boolean = withContext(Dispatchers.IO) {
        try {
            val token = getCurrentUser()?.sessionToken
            !token.isNullOrEmpty()
        } catch (e: ParseException) {
            Log.e("SessionToken", "isSessionValid: ${e.message}")
            false
        }
    }

    suspend fun availableSession(): Boolean = withContext(Dispatchers.IO) {
        try {
            val token = ParseUser.getCurrentSessionToken()
            !token.isNullOrEmpty()
        } catch (e: ParseException) {
            Log.e("SessionToken", "availableSession: ${e.message}")
            false
        }
    }

    suspend fun checkSession(): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            if (isInternetAvailable().value) {
                isSessionValid()
            } else {
                availableSession()
            }
        } catch (e: Exception) {
            Log.e("SessionToken", "checkSession: ${e.message}")
            false
        }
    }

}