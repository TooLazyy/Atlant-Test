package com.example.i_token

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.example.base.SimpleKeyValueStorage
import com.google.gson.Gson

private const val KEY_TOKEN = "token"

@SuppressLint("ApplySharedPref")
class TokenStorage(
    private val preferences: SharedPreferences,
    private val gson: Gson
) : SimpleKeyValueStorage<TokenData> {

    override fun get(): TokenData? {
        val tokenString = preferences.getString(KEY_TOKEN, "") ?: ""
        return if (tokenString.isEmpty()) {
            null
        } else {
            gson.fromJson(tokenString, TokenData::class.java)
        }
    }

    override fun put(value: TokenData) {
        val tokenString = gson.toJson(value)
        preferences
            .edit()
            .putString(KEY_TOKEN, tokenString)
            .commit()
    }

    override fun clear() {
        preferences
            .edit()
            .remove(KEY_TOKEN)
            .commit()
    }
}