package br.com.mygoals.base

import androidx.databinding.ObservableField

abstract class BaseViewState {
    var isLoading: ObservableField<Boolean> = ObservableField(false)
    var isSuccess: ObservableField<Boolean> = ObservableField(false)
    var isError: ObservableField<Boolean> = ObservableField(false)
    var errorThrowable: ObservableField<Throwable?> = ObservableField(Exception())

    open fun onLoading() {
        isLoading.set(true)
        isSuccess.set(false)
        isError.set(false)
        errorThrowable.set(Exception())
    }

    open fun onSuccess() {
        isLoading.set(false)
        isSuccess.set(true)
        isError.set(false)
        errorThrowable.set(Exception())
    }

    open fun onError(error: Throwable) {
        isLoading.set(false)
        isSuccess.set(false)
        isError.set(true)
        errorThrowable.set(error)
    }

}
