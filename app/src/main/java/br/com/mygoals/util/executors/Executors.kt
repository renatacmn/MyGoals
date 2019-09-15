package br.com.mygoals.util.executors

import io.reactivex.Scheduler

interface Executors {

    fun diskIO(): Scheduler

    fun networkIO(): Scheduler

    fun mainThread(): Scheduler
}