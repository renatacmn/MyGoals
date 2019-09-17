package br.com.mygoals.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseRepository {

    private val compositeDisposable = CompositeDisposable()

    fun dispose() {
        compositeDisposable.dispose()
    }

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}
