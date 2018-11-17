package blekel.sample.chucknorris.presentation.settings


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import blekel.sample.chucknorris.R
import timber.log.Timber

/**
 * Created by Vitaliy Levonyuk on 17.11.2018
 */

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val keys = HashMap<String, Int>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listOf(
            R.string.pref_user_firstname_key,
            R.string.pref_user_lastname_key,
            R.string.pref_offline_mode_key
        ).forEach { keyId ->
            val key = getString(keyId)
            keys[key] = keyId
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        updatePrefSummary(R.string.pref_user_firstname_key)
        updatePrefSummary(R.string.pref_user_lastname_key)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val preference = findPreference(key)
        Timber.d("--- $key -> $preference");
        val keyId = keys[key] ?: throw NullPointerException("Key not found: $key")

        when (keyId) {
            R.string.pref_user_firstname_key, R.string.pref_user_lastname_key -> {
                val value = sharedPreferences.getString(key, "")
                Timber.d("--- value: $value");
                updatePrefSummary(keyId)
            }
            R.string.pref_offline_mode_key -> {
                val value = sharedPreferences.getBoolean(key, false)
                Timber.d("--- value: $value");
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)

    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun updatePrefSummary(keyId: Int) {
        val key = getString(keyId)

        val sharedPreferences = preferenceManager.sharedPreferences
        val preferenceScreen = preferenceManager.preferenceScreen

        preferenceScreen.findPreference(key).summary = sharedPreferences.getString(key, "")
    }
}
