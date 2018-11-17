package blekel.sample.chucknorris.domain

import blekel.sample.chucknorris.data.repository.SettingsRepository
import dagger.Reusable
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 17.11.2018
 */

@Reusable
class SettingsInteractor @Inject constructor(
    private val repository: SettingsRepository
) {

    fun getUserName(): String {
        return (getUserFirstname() + " " + getUserLastname()).trim()
    }

    fun isUserOverride() = getUserFirstname().isNotBlank()
            || getUserLastname().isNotBlank()

    fun isOfflineMode() = repository.isOfflineMode()

    private fun getUserFirstname() = repository.getUserFirstname()

    private fun getUserLastname() = repository.getUserLastname()
}
