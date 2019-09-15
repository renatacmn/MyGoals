package br.com.mygoals.base.injection

import android.content.Context
import br.com.mygoals.base.application.MyGoalsApplication
import br.com.mygoals.base.injection.modules.ActivityBindings
import br.com.mygoals.base.injection.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ActivityBindings::class
    ]
)
interface AppComponent : AndroidInjector<MyGoalsApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}
