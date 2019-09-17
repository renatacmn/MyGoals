package br.com.mygoals.base

import br.com.mygoals.util.executors.Executors
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class BaseRepository2(
    private val executors: Executors
) {

    private val compositeDisposable = CompositeDisposable()

    fun dispose() {
        compositeDisposable.dispose()
    }

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    abstract fun loadFromDbRefreshingIfNecessary()

    protected fun <T> loadFromDbRefreshingIfNecessary(single: Single<T>) {
        Timber.d("Check if exists")
        add(
            single.subscribeOn(executors.diskIO())
                .subscribe(
                    (this::onCheckIfExistsSuccess),
                    (this::onCheckIfExistsError)
                )
        )
    }

    private fun <T> onCheckIfExistsSuccess(data: T?) {
        val exists = data != null
        if (!exists) {
            Timber.d("> Doesn't exist. Will load from API")
            loadFromApi()
        } else {
            Timber.d("> Exists. Will load from DB")
            loadFromDb()
        }
    }

    private fun onCheckIfExistsError(error: Throwable) {
        Timber.d("> Error while checking if exists. Will load from API\n>>${error.message}")
        error.printStackTrace()
        loadFromApi()
    }

    abstract fun loadFromApi()

    protected fun <T> loadFromApi(single: Single<T>) {
        Timber.d("Load from API")
        add(
            single.subscribeOn(executors.networkIO())
                .observeOn(executors.diskIO())
                .subscribe(
                    (this::onLoadFromApiSuccess),
                    (this::onLoadFromApiError)
                )
        )
    }

    abstract fun <T> onLoadFromApiSuccess(data: T)

    private fun onLoadFromApiError(error: Throwable) {
        Timber.d("> Error loading from API. Will show error state")
        error.printStackTrace()
        emitError(error)
    }

    abstract fun loadFromDb()

    protected fun <T> loadFromDb(single: Single<T>) {
        Timber.d("Load from DB")
        add(
            single.subscribeOn(executors.diskIO())
                .observeOn(executors.mainThread())
                .subscribe(
                    (this::onLoadFromDbSuccess),
                    (this::onLoadFromDbError)
                )
        )
    }

    abstract fun <T> onLoadFromDbSuccess(data: T)

    private fun onLoadFromDbError(error: Throwable) {
        Timber.d("> Error loading from DB. Will show error state\n>>${error.message}")
        emitError(error)
    }

    abstract fun emitError(error: Throwable)

}
