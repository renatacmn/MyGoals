package br.com.mygoals.base.repository

import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.dao.RuleDao
import br.com.mygoals.testUtil.BaseRepositoryTest
import br.com.mygoals.testUtil.RulesRepositoryTestDataUtil
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RulesRepositoryTest : BaseRepositoryTest() {

    @Mock
    private lateinit var api: MyGoalsApi

    @Mock
    private lateinit var ruleDao: RuleDao

    private lateinit var rulesRepository: RulesRepository

    private val testDataUtil = RulesRepositoryTestDataUtil()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        rulesRepository = RulesRepository(api, ruleDao)
    }

    @Test
    fun getSavingsRules_ifHasRulesSaved_shouldLoadFromDatabase() {
        whenever(ruleDao.hasRules(any()))
            .thenReturn(Single.just(testDataUtil.ruleEntity()))

        val result = rulesRepository.getSavingsRules().test()
        result.awaitTerminalEvent()

        verify(ruleDao, times(1)).loadRules()
    }

    @Test
    fun getSavingsRules_ifHasRulesSaved_shouldNotCallApi() {
        whenever(ruleDao.hasRules(any()))
            .thenReturn(Single.just(testDataUtil.ruleEntity()))

        rulesRepository.getSavingsRules()
        verifyZeroInteractions(api)
    }

    @Test
    fun getSavingsRules_ifDoesNotHaveRulesSaved_shouldCallApi() {
        whenever(ruleDao.hasRules(any()))
            .thenReturn(Single.error(RuntimeException()))

        val result = rulesRepository.getSavingsRules().test()
        result.awaitTerminalEvent()

        verify(api, times(1)).getSavingsRules()
    }

    @Test
    fun getSavingsRules_onApiSuccess_shouldSaveOnDatabase() {
        whenever(ruleDao.hasRules(any()))
            .thenReturn(Single.error(RuntimeException()))

        whenever(api.getSavingsRules())
            .thenReturn(Single.just(testDataUtil.savingsRulesApiModel()))

        val result = rulesRepository.getSavingsRules().test()
        result.awaitTerminalEvent()

        verify(ruleDao, times(1)).saveRules(any())
    }

    @Test
    fun getSavingsRules_onApiSuccess_shouldLoadFromDatabase() {
        whenever(ruleDao.hasRules(any()))
            .thenReturn(Single.error(RuntimeException()))

        whenever(api.getSavingsRules())
            .thenReturn(Single.just(testDataUtil.savingsRulesApiModel()))

        val result = rulesRepository.getSavingsRules().test()
        result.awaitTerminalEvent()

        verify(ruleDao, times(1)).loadRules()
    }

}
