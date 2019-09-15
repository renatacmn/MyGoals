package br.com.mygoals.base.injection.modules

import br.com.mygoals.util.executors.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesAppExecutors(): AppExecutors = AppExecutors()

}
