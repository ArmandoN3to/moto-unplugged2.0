package com.example.motounplugged.database.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "focus_preferences"

val Context.focusDataStore by preferencesDataStore(
    name = DATASTORE_NAME
)

object FocusKeys {
    val FOCUS_ACTIVE = booleanPreferencesKey("focus_active")
    val FOCUS_PROFILE_ID = longPreferencesKey("focus_profile_id")
    val FOCUS_END_TIME = longPreferencesKey("focus_end_time")
    val FOCUS_PASSWORD_REQUIRED = booleanPreferencesKey("focus_password_required")

}

class FocusDataStore(private val context: Context) {

    val isFocusActive: Flow<Boolean> =
        context.focusDataStore.data.map {
            it[FocusKeys.FOCUS_ACTIVE] ?: false
        }

    suspend fun setFocusActive(
        active: Boolean,
        profileId: Long? = null,
        endTime: Long? = null,
        passwordRequired: Boolean = false
    ) {
        context.focusDataStore.edit { prefs ->
            prefs[FocusKeys.FOCUS_ACTIVE] = active
            prefs[FocusKeys.FOCUS_PASSWORD_REQUIRED] = passwordRequired
            profileId?.let { prefs[FocusKeys.FOCUS_PROFILE_ID] = it }
            endTime?.let { prefs[FocusKeys.FOCUS_END_TIME] = it }
        }
    }

    suspend fun clearFocus() {
        context.focusDataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
