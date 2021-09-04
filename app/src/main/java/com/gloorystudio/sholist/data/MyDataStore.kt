package com.gloorystudio.sholist.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gloorystudio.sholist.CurrentData
import com.gloorystudio.sholist.data.db.entity.User
import com.gloorystudio.sholist.decrypt
import com.gloorystudio.sholist.encrypt
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val JWT_KEY = stringPreferencesKey("newJwt")
val USER_ID_KEY = intPreferencesKey("newUserId")
val USER_MAIL_KEY = stringPreferencesKey("email")
val USER_NAME_KEY = stringPreferencesKey("name")
val USER_USERNAME_KEY = stringPreferencesKey("username")
val ITEM_VERSION = stringPreferencesKey("itemVersion")
val NIGHT_MODE = intPreferencesKey("nightMode")
private const val pwJwt = "VsWa@WjoK#9E1F0a"
private const val pwUser = "N3ACFtp%v20kwA5b"

suspend fun setJwt(context: Context, jwt: String) {
    context.dataStore.edit { settings ->
        settings[JWT_KEY] = jwt.encrypt(pwJwt)
        CurrentData.currentJwt = jwt
    }
}

suspend fun setUserData(context: Context, user: User) {
    context.dataStore.edit { settings ->
        settings[USER_ID_KEY] = user.id
        settings[USER_MAIL_KEY] = user.email!!.encrypt(pwUser)
        settings[USER_NAME_KEY] = user.name!!.encrypt(pwUser)
        settings[USER_USERNAME_KEY] = user.username!!.encrypt(pwUser)
        CurrentData.currentUser = user
    }
}

suspend fun getJwt(context: Context): String? {
    val preferences = context.dataStore.data.first()
    val newJwt = preferences[JWT_KEY]?.decrypt(pwJwt)
    CurrentData.currentJwt = newJwt
    Log.d(TAG, "getJwt: $newJwt")
    return newJwt
}

suspend fun getUserData(context: Context): User? {
    val preferences = context.dataStore.data.first()
    return if (preferences[USER_ID_KEY] == null || preferences[USER_ID_KEY] == -1)
        null
    else {
        val user = User(
            id = preferences[USER_ID_KEY]!!,
            email = preferences[USER_MAIL_KEY]?.decrypt(pwUser),
            name = preferences[USER_NAME_KEY]?.decrypt(pwUser),
            username = preferences[USER_USERNAME_KEY]?.decrypt(pwUser),
            verification = null,
            status = null,
            deviceId = null,
            shoppingListId = null
        )
        CurrentData.currentUser = user
        user
    }
}

suspend fun setItemVersion(context: Context, version: String) {
    context.dataStore.edit { settings ->
        settings[ITEM_VERSION] = version.encrypt(pwUser)
    }
}

suspend fun getItemVersion(context: Context): String? {
    val preferences = context.dataStore.data.first()
    return preferences[ITEM_VERSION]?.decrypt(pwUser)
}

suspend fun setNightMode(context: Context, mode: Int) {
    context.dataStore.edit { settings ->
        settings[NIGHT_MODE] = mode
    }
}

suspend fun getNightMode(context: Context): Int? {
    val preferences = context.dataStore.data.first()
    return preferences[NIGHT_MODE]
}

suspend fun logout(context: Context) {
    context.dataStore.edit {
        it.clear()
    }
}
