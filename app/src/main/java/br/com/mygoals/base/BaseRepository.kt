package br.com.mygoals.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.Calendar
import java.util.Date

abstract class BaseRepository {

    private val compositeDisposable = CompositeDisposable()

    fun dispose() {
        compositeDisposable.dispose()
    }

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun getMaxRefreshTime(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES)
        return calendar.time
    }

    companion object {
        private const val FRESH_TIMEOUT_IN_MINUTES = 2
    }

}
