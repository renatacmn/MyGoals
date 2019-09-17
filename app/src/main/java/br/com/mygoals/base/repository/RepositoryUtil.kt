package br.com.mygoals.base.repository

import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class RepositoryUtil @Inject constructor() {

    fun getMaxRefreshTime(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES)
        return calendar.time
    }

    companion object {
        private const val FRESH_TIMEOUT_IN_MINUTES = 2
    }

}
