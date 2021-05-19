package com.gloorystudio.sholist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gloorystudio.sholist.decrypt
import com.gloorystudio.sholist.encrypt
import com.gloorystudio.sholist.data.db.entity.User
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val JWT_KEY = stringPreferencesKey("jwt")
val USER_ID_KEY = stringPreferencesKey("userId")
val USER_MAIL_KEY = stringPreferencesKey("email")
val USER_NAME_KEY = stringPreferencesKey("name")
val USER_USERNAME_KEY = stringPreferencesKey("username")
private const val pwJwt ="VsWa@WjoK#9E1F0a"
private const val pwUser ="N3ACFtp%v20kwA5b"

suspend fun setJwt(context: Context, jwt: String) {
    context.dataStore.edit { settings ->
        settings[JWT_KEY] = jwt.encrypt(pwJwt)
    }
}

suspend fun setUserData(context: Context, user: User) {
    context.dataStore.edit { settings ->
        settings[USER_ID_KEY] = user.id.encrypt(pwUser)
        settings[USER_MAIL_KEY] = user.email!!.encrypt(pwUser)
        settings[USER_NAME_KEY] = user.name!!.encrypt(pwUser)
        settings[USER_USERNAME_KEY] = user.username!!.encrypt(pwUser)
    }
}

suspend fun getJwt(context: Context): String? {
    val preferences = context.dataStore.data.first()
    return preferences[JWT_KEY]?.decrypt(pwJwt)
}

suspend fun getUserData(context: Context): User? {
    val preferences = context.dataStore.data.first()
    return if (preferences[USER_ID_KEY]==null||preferences[USER_ID_KEY]=="")
        null
    else {
        User(
            id = preferences[USER_ID_KEY]!!.decrypt(pwUser),
            email = preferences[USER_MAIL_KEY]?.decrypt(pwUser),
            name = preferences[USER_NAME_KEY]?.decrypt(pwUser),
            username = preferences[USER_USERNAME_KEY]?.decrypt(pwUser),
            verification = null,
            status = null,
            deviceId = null,
            shoppingListId = null
        )
    }

}


