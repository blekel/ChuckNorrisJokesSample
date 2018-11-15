package blekel.sample.chucknorris.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

@Module
class DatabaseModule {

    companion object {
        private const val DB_VERSION = 1
    }

    @Provides
    @Singleton
    fun provideRealm(context: Context): Realm {
        val config = RealmConfiguration.Builder()
            .schemaVersion(DB_VERSION.toLong())
            .deleteRealmIfMigrationNeeded()
            .compactOnLaunch { totalBytes, usedBytes -> true }
            .build()

        Realm.setDefaultConfiguration(config)

        return Realm.getDefaultInstance()
    }
}