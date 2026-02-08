package com.example.pos.data.local

import android.content.Context
import android.content.SharedPreferences
import io.github.jan.supabase.gotrue.SessionManager
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedPreferencesSessionManager(context: Context) : SessionManager {

    private val prefs: SharedPreferences = context.getSharedPreferences("supabase_session", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun saveSession(session: UserSession) {
        val sessionString = json.encodeToString(session)
        prefs.edit().putString("session", sessionString).apply()
    }

    override suspend fun loadSession(): UserSession? {
        val sessionString = prefs.getString("session", null) ?: return null
        return try {
            json.decodeFromString<UserSession>(sessionString)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteSession() {
        prefs.edit().remove("session").apply()
    }
}
