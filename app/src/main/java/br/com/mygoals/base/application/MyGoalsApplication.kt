package br.com.mygoals.base.application

import br.com.mygoals.BuildConfig
import br.com.mygoals.base.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

open class MyGoalsApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .factory()
            .create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        configureLogging()
    }

    private fun configureLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
