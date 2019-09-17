package br.com.mygoals.testUtil

import br.com.mygoals.util.executors.Executors
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Singleton
open class TestExecutors(
    private val diskIO: Scheduler = AndroidSchedulers.mainThread(),
    private val networkIO: Scheduler = AndroidSchedulers.mainThread(),
    private val mainThread: Scheduler = AndroidSchedulers.mainThread()
) : Executors {

    override fun diskIO(): Scheduler {
        return diskIO
    }

    override fun networkIO(): Scheduler {
        return networkIO
    }

    override fun mainThread(): Scheduler {
        return mainThread
    }

}