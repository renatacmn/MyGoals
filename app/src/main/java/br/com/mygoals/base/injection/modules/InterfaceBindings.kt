package br.com.mygoals.base.injection.modules

import br.com.mygoals.util.executors.AppExecutors
import br.com.mygoals.util.executors.Executors
import dagger.Binds
import dagger.Module

@Module
abstract class InterfaceBindings {

    @Binds
    abstract fun bindExecutors(appExecutors: AppExecutors): Executors

}
