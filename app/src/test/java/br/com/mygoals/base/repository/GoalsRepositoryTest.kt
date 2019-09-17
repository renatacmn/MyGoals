package br.com.mygoals.base.repository

import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.dao.GoalDao
import br.com.mygoals.testUtil.BaseRepositoryTest
import br.com.mygoals.testUtil.GoalsRepositoryTestDataUtil
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GoalsRepositoryTest : BaseRepositoryTest() {

    @Mock
    private lateinit var api: MyGoalsApi

    @Mock
    private lateinit var goalDao: GoalDao

    private lateinit var goalsRepository: GoalsRepository

    private val testDataUtil = GoalsRepositoryTestDataUtil()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        goalsRepository = GoalsRepository(api, goalDao)
    }

    @Test
    fun getSavingsGoals_ifHasGoalsSaved_shouldLoadFromDatabase() {
        whenever(goalDao.hasGoals(any()))
            .thenReturn(Single.just(testDataUtil.goalEntity()))

        val result = goalsRepository.getSavingsGoals().test()
        result.awaitTerminalEvent()

        verify(goalDao, times(1)).loadGoals()
    }

    @Test
    fun getSavingsGoals_ifHasGoalsSaved_shouldNotCallApi() {
        whenever(goalDao.hasGoals(any()))
            .thenReturn(Single.just(testDataUtil.goalEntity()))

        goalsRepository.getSavingsGoals()
        verifyZeroInteractions(api)
    }

    @Test
    fun getSavingsGoals_ifDoesNotHaveGoalsSaved_shouldCallApi() {
        whenever(goalDao.hasGoals(any()))
            .thenReturn(Single.error(RuntimeException()))

        val result = goalsRepository.getSavingsGoals().test()
        result.awaitTerminalEvent()

        verify(api, times(1)).getSavingsGoals()
    }

    @Test
    fun getSavingsGoals_onApiSuccess_shouldSaveOnDatabase() {
        whenever(goalDao.hasGoals(any()))
            .thenReturn(Single.error(RuntimeException()))

        whenever(api.getSavingsGoals())
            .thenReturn(Single.just(testDataUtil.savingsGoalsApiModel()))

        val result = goalsRepository.getSavingsGoals().test()
        result.awaitTerminalEvent()

        verify(goalDao, times(1)).saveGoals(any())
    }

    @Test
    fun getSavingsGoals_onApiSuccess_shouldLoadFromDatabase() {
        whenever(goalDao.hasGoals(any()))
            .thenReturn(Single.error(RuntimeException()))

        whenever(api.getSavingsGoals())
            .thenReturn(Single.just(testDataUtil.savingsGoalsApiModel()))

        val result = goalsRepository.getSavingsGoals().test()
        result.awaitTerminalEvent()

        verify(goalDao, times(1)).loadGoals()
    }

}
