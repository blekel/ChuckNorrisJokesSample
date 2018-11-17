package blekel.sample.chucknorris.data.repository

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.preference.PreferenceManager
import blekel.sample.chucknorris.R
import dagger.Reusable
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 17.11.2018
 */

@Reusable
class SettingsRepository @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getUserFirstname() = getStringPref(R.string.pref_user_firstname_key)

    fun getUserLastname() = getStringPref(R.string.pref_user_lastname_key)

    fun isOfflineMode() = getBooleanPref(R.string.pref_offline_mode_key)

    private fun getStringPref(@StringRes keyId: Int): String {
        return sharedPreferences.getString(getKey(keyId), "")!!
    }

    private fun getBooleanPref(@StringRes keyId: Int): Boolean {
        return sharedPreferences.getBoolean(getKey(keyId), false)
    }

    private fun getKey(@StringRes keyId: Int) = context.getString(keyId)
}
