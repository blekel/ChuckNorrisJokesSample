package blekel.sample.chucknorris.di

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideSensorManager(): SensorManager =
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
}
