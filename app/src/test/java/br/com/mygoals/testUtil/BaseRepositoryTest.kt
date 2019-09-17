package br.com.mygoals.testUtil

import org.mockito.Mockito

abstract class BaseRepositoryTest {

    protected fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

}
